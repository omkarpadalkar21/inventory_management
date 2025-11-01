import { useState, useEffect } from "react";
import { Sidebar } from "@/components/Sidebar";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Plus, TrendingUp, TrendingDown, RefreshCw } from "lucide-react";
import { apiClient } from "@/lib/api";
import { useToast } from "@/hooks/use-toast";
import { Badge } from "@/components/ui/badge";

const TransactionDialog = ({
  isDialogOpen,
  setIsDialogOpen,
  transactionType,
  formData,
  setFormData,
  products,
  handleSubmit,
  resetForm,
}: any) => (
  <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
    <DialogContent>
      <DialogHeader>
        <DialogTitle>
          {transactionType === "stock-in"
            ? "Stock In"
            : transactionType === "stock-out"
            ? "Stock Out"
            : "Adjust Stock"}
        </DialogTitle>
        <DialogDescription>
          {transactionType === "stock-in"
            ? "Add stock to inventory"
            : transactionType === "stock-out"
            ? "Remove stock from inventory"
            : "Adjust stock quantity"}
        </DialogDescription>
      </DialogHeader>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <Label htmlFor="product">Product *</Label>
          <Select
            value={formData.productId}
            onValueChange={(value) =>
              setFormData({ ...formData, productId: value })
            }
            required
          >
            <SelectTrigger>
              <SelectValue placeholder="Select product" />
            </SelectTrigger>
            <SelectContent>
              {products.map((product: any) => (
                <SelectItem key={product.id} value={product.id}>
                  {product.name} ({product.sku}) - Stock:{" "}
                  {product.quantityInStock}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        <div>
          <Label htmlFor="quantity">
            Quantity * {transactionType === "adjust" && "(+ or -)"}
          </Label>
          <Input
            id="quantity"
            type="number"
            min={transactionType === "adjust" ? undefined : "1"}
            value={formData.quantity}
            onChange={(e) =>
              setFormData({ ...formData, quantity: e.target.value })
            }
            required
          />
        </div>

        <div>
          <Label htmlFor="reference">Reference Number</Label>
          <Input
            id="reference"
            value={formData.referenceNumber}
            onChange={(e) =>
              setFormData({ ...formData, referenceNumber: e.target.value })
            }
            maxLength={50}
          />
        </div>

        <div>
          <Label htmlFor="notes">
            Notes {transactionType === "adjust" && "*"}
          </Label>
          <Textarea
            id="notes"
            value={formData.notes}
            onChange={(e) =>
              setFormData({ ...formData, notes: e.target.value })
            }
            rows={3}
            required={transactionType === "adjust"}
          />
        </div>

        <DialogFooter>
          <Button
            type="button"
            variant="outline"
            onClick={() => {
              setIsDialogOpen(false);
              resetForm();
            }}
          >
            Cancel
          </Button>
          <Button type="submit">Submit</Button>
        </DialogFooter>
      </form>
    </DialogContent>
  </Dialog>
);

export default function Transactions() {
  const [transactions, setTransactions] = useState<any[]>([]);
  const [products, setProducts] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [transactionType, setTransactionType] = useState<
    "stock-in" | "stock-out" | "adjust"
  >("stock-in");
  const { toast } = useToast();

  const [formData, setFormData] = useState({
    productId: "",
    quantity: "",
    referenceNumber: "",
    notes: "",
  });

  useEffect(() => {
    loadTransactions();
    loadProducts();
  }, []);

  const loadTransactions = async () => {
    try {
      setLoading(true);
      const data = await apiClient.getStockTransactions();
      setTransactions(data);
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.message,
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  const loadProducts = async () => {
    try {
      const response = await apiClient.getProducts(0, 1000);
      setProducts(response.data);
    } catch (error: any) {
      toast({
        title: "Error loading products",
        description: error.message,
        variant: "destructive",
      });
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const payload = {
        productId: formData.productId,
        quantity: parseInt(formData.quantity),
        referenceNumber: formData.referenceNumber || undefined,
        notes: formData.notes || undefined,
      };

      if (transactionType === "stock-in") {
        await apiClient.stockIn(payload);
        toast({ title: "Stock added successfully" });
      } else if (transactionType === "stock-out") {
        await apiClient.stockOut(payload);
        toast({ title: "Stock removed successfully" });
      } else {
        await apiClient.adjustStock({ ...payload, notes: formData.notes });
        toast({ title: "Stock adjusted successfully" });
      }

      setIsDialogOpen(false);
      loadTransactions();
      resetForm();
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.message,
        variant: "destructive",
      });
    }
  };

  const resetForm = () => {
    setFormData({
      productId: "",
      quantity: "",
      referenceNumber: "",
      notes: "",
    });
  };

  const openDialog = (type: "stock-in" | "stock-out" | "adjust") => {
    setTransactionType(type);
    setIsDialogOpen(true);
  };

  const getTransactionIcon = (type: string) => {
    if (type === "STOCK_IN")
      return <TrendingUp className="h-4 w-4 text-green-600" />;
    if (type === "STOCK_OUT")
      return <TrendingDown className="h-4 w-4 text-red-600" />;
    return <RefreshCw className="h-4 w-4 text-blue-600" />;
  };

  const getTransactionBadge = (type: string) => {
    if (type === "STOCK_IN")
      return <Badge className="bg-green-100 text-green-800">Stock In</Badge>;
    if (type === "STOCK_OUT")
      return <Badge className="bg-red-100 text-red-800">Stock Out</Badge>;
    return <Badge className="bg-blue-100 text-blue-800">Adjustment</Badge>;
  };

  return (
    <div className="flex h-screen">
      <Sidebar />
      <div className="flex-1 overflow-auto">
        <div className="container mx-auto p-6">
          <div className="flex justify-between items-center mb-6">
            <h1 className="text-3xl font-bold">Stock Transactions</h1>
            <div className="flex gap-2">
              <Button onClick={() => openDialog("stock-in")} variant="default">
                <TrendingUp className="mr-2 h-4 w-4" /> Stock In
              </Button>
              <Button
                onClick={() => openDialog("stock-out")}
                variant="destructive"
              >
                <TrendingDown className="mr-2 h-4 w-4" /> Stock Out
              </Button>
              <Button onClick={() => openDialog("adjust")} variant="outline">
                <RefreshCw className="mr-2 h-4 w-4" /> Adjust
              </Button>
            </div>
          </div>

          <Card>
            <CardHeader>
              <CardTitle>Transaction History</CardTitle>
            </CardHeader>
            <CardContent>
              {loading ? (
                <div className="text-center py-8">Loading...</div>
              ) : (
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Type</TableHead>
                      <TableHead>Product</TableHead>
                      <TableHead>Quantity</TableHead>
                      <TableHead>Reference</TableHead>
                      <TableHead>Performed By</TableHead>
                      <TableHead>Date</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {transactions.map((transaction) => (
                      <TableRow key={transaction.id}>
                        <TableCell>
                          <div className="flex items-center gap-2">
                            {getTransactionIcon(transaction.transactionType)}
                            {getTransactionBadge(transaction.transactionType)}
                          </div>
                        </TableCell>
                        <TableCell className="font-medium">
                          {transaction.productName}
                        </TableCell>
                        <TableCell>
                          <span
                            className={
                              transaction.transactionType === "STOCK_IN"
                                ? "text-green-600"
                                : transaction.transactionType === "STOCK_OUT"
                                ? "text-red-600"
                                : ""
                            }
                          >
                            {transaction.transactionType === "STOCK_IN"
                              ? "+"
                              : transaction.transactionType === "STOCK_OUT"
                              ? "-"
                              : ""}
                            {transaction.quantity}
                          </span>
                        </TableCell>
                        <TableCell>
                          {transaction.referenceNumber || "-"}
                        </TableCell>
                        <TableCell>{transaction.performedByUsername}</TableCell>
                        <TableCell>
                          {new Date(
                            transaction.transactionDate
                          ).toLocaleString()}
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              )}
            </CardContent>
          </Card>
        </div>
      </div>

      <TransactionDialog
        isDialogOpen={isDialogOpen}
        setIsDialogOpen={setIsDialogOpen}
        transactionType={transactionType}
        formData={formData}
        setFormData={setFormData}
        products={products}
        handleSubmit={handleSubmit}
        resetForm={resetForm}
      />
    </div>
  );
}
