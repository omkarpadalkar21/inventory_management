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
import { Plus, Edit, Trash2, Eye } from "lucide-react";
import { apiClient } from "@/lib/api";
import { useToast } from "@/hooks/use-toast";
import { Badge } from "@/components/ui/badge";

const formatINR = (value: number | string) =>
  new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', maximumFractionDigits: 2 }).format(Number(value ?? 0));

const PurchaseOrderDialog = ({
  isAddDialogOpen,
  setIsAddDialogOpen,
  formData,
  setFormData,
  suppliers,
  products,
  handleSubmit,
  addItem,
  removeItem,
  updateItem,
  resetForm,
}: any) => (
  <Dialog open={isAddDialogOpen} onOpenChange={setIsAddDialogOpen}>
    <DialogContent className="max-w-4xl max-h-[90vh] overflow-y-auto">
      <DialogHeader>
        <DialogTitle>Create Purchase Order</DialogTitle>
        <DialogDescription>Add a new purchase order</DialogDescription>
      </DialogHeader>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="grid grid-cols-2 gap-4">
          <div>
            <Label htmlFor="supplier">Supplier *</Label>
            <Select
              value={formData.supplierId}
              onValueChange={(value) =>
                setFormData({ ...formData, supplierId: value })
              }
              required
            >
              <SelectTrigger>
                <SelectValue placeholder="Select supplier" />
              </SelectTrigger>
              <SelectContent>
                {suppliers.map((supplier: any) => (
                  <SelectItem key={supplier.id} value={supplier.id}>
                    {supplier.name}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
          <div>
            <Label htmlFor="orderDate">Order Date *</Label>
            <Input
              id="orderDate"
              type="date"
              value={formData.orderDate}
              onChange={(e) =>
                setFormData({ ...formData, orderDate: e.target.value })
              }
              required
            />
          </div>
        </div>

        <div>
          <Label htmlFor="expectedDelivery">Expected Delivery Date</Label>
          <Input
            id="expectedDelivery"
            type="date"
            value={formData.expectedDeliveryDate}
            onChange={(e) =>
              setFormData({
                ...formData,
                expectedDeliveryDate: e.target.value,
              })
            }
          />
        </div>

        <div className="border rounded-lg p-4">
          <div className="flex justify-between items-center mb-4">
            <Label>Items *</Label>
            <Button type="button" onClick={addItem} size="sm">
              <Plus className="h-4 w-4 mr-1" /> Add Item
            </Button>
          </div>

          {formData.items.map((item: any, index: number) => (
            <div
              key={index}
              className="grid grid-cols-12 gap-2 mb-3 items-end"
            >
              <div className="col-span-5">
                <Label>Product</Label>
                <Select
                  value={item.productId}
                  onValueChange={(value) =>
                    updateItem(index, "productId", value)
                  }
                  required
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Select product" />
                  </SelectTrigger>
                  <SelectContent>
                    {products.map((product: any) => (
                      <SelectItem key={product.id} value={product.id}>
                        {product.name} - {formatINR(product.unitPrice)}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
              <div className="col-span-3">
                <Label>Quantity</Label>
                <Input
                  type="number"
                  min="1"
                  value={item.quantity}
                  onChange={(e) =>
                    updateItem(index, "quantity", e.target.value)
                  }
                  required
                />
              </div>
              <div className="col-span-3">
                <Label>Unit Price</Label>
                <Input
                  type="number"
                  step="0.01"
                  min="0"
                  value={item.unitPrice}
                  onChange={(e) =>
                    updateItem(index, "unitPrice", e.target.value)
                  }
                  required
                />
              </div>
              <div className="col-span-1">
                <Button
                  type="button"
                  variant="destructive"
                  size="icon"
                  onClick={() => removeItem(index)}
                  disabled={formData.items.length === 1}
                >
                  <Trash2 className="h-4 w-4" />
                </Button>
              </div>
            </div>
          ))}
        </div>

        <DialogFooter>
          <Button
            type="button"
            variant="outline"
            onClick={() => {
              setIsAddDialogOpen(false);
              resetForm();
            }}
          >
            Cancel
          </Button>
          <Button type="submit">Create Order</Button>
        </DialogFooter>
      </form>
    </DialogContent>
  </Dialog>
);

const ViewOrderDialog = ({
  isViewDialogOpen,
  setIsViewDialogOpen,
  selectedOrder,
  getStatusBadge,
}: any) => (
  <Dialog open={isViewDialogOpen} onOpenChange={setIsViewDialogOpen}>
    <DialogContent className="max-w-3xl">
      <DialogHeader>
        <DialogTitle>Purchase Order Details</DialogTitle>
      </DialogHeader>
      {selectedOrder && (
        <div className="space-y-4">
          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label>Order Number</Label>
              <p className="text-sm font-medium">
                {selectedOrder.orderNumber}
              </p>
            </div>
            <div>
              <Label>Status</Label>
              <div className="mt-1">
                {getStatusBadge(selectedOrder.status)}
              </div>
            </div>
            <div>
              <Label>Supplier</Label>
              <p className="text-sm">{selectedOrder.supplierName}</p>
            </div>
            <div>
              <Label>Order Date</Label>
              <p className="text-sm">
                {new Date(selectedOrder.orderDate).toLocaleDateString()}
              </p>
            </div>
            <div>
              <Label>Total Amount</Label>
              <p className="text-sm font-bold">
                {formatINR(selectedOrder.totalAmount)}
              </p>
            </div>
            <div>
              <Label>Created By</Label>
              <p className="text-sm">{selectedOrder.createdByUsername}</p>
            </div>
          </div>

          <div>
            <Label>Items</Label>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Product</TableHead>
                  <TableHead>Quantity</TableHead>
                  <TableHead>Unit Price</TableHead>
                  <TableHead>Subtotal</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {selectedOrder.items?.map((item: any) => (
                  <TableRow key={item.id}>
                    <TableCell>{item.productName}</TableCell>
                    <TableCell>{item.quantity}</TableCell>
                    <TableCell>{formatINR(item.unitPrice)}</TableCell>
                    <TableCell>{formatINR(item.subtotal)}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </div>
        </div>
      )}
    </DialogContent>
  </Dialog>
);

export default function PurchaseOrders() {
  const [purchaseOrders, setPurchaseOrders] = useState<any[]>([]);
  const [suppliers, setSuppliers] = useState<any[]>([]);
  const [products, setProducts] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [isAddDialogOpen, setIsAddDialogOpen] = useState(false);
  const [isViewDialogOpen, setIsViewDialogOpen] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState<any>(null);
  const { toast } = useToast();

  const [formData, setFormData] = useState({
    supplierId: "",
    orderDate: "",
    expectedDeliveryDate: "",
    items: [
      {
        productId: "",
        quantity: "",
        unitPrice: "",
      },
    ],
  });

  useEffect(() => {
    loadPurchaseOrders();
    loadSuppliers();
    loadProducts();
  }, []);

  const loadPurchaseOrders = async () => {
    try {
      setLoading(true);
      const data = await apiClient.getPurchaseOrders();
      setPurchaseOrders(data);
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

  const loadSuppliers = async () => {
    try {
      const data = await apiClient.getSuppliers();
      setSuppliers(data);
    } catch (error: any) {
      toast({
        title: "Error loading suppliers",
        description: error.message,
        variant: "destructive",
      });
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
        supplierId: formData.supplierId,
        orderDate: formData.orderDate,
        expectedDeliveryDate: formData.expectedDeliveryDate || undefined,
        items: formData.items.map((item) => ({
          productId: item.productId,
          quantity: parseInt(item.quantity),
          unitPrice: parseFloat(item.unitPrice),
        })),
      };

      await apiClient.createPurchaseOrder(payload);
      toast({ title: "Purchase order created successfully" });
      setIsAddDialogOpen(false);
      loadPurchaseOrders();
      resetForm();
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.message,
        variant: "destructive",
      });
    }
  };

  const handleStatusChange = async (id: string, status: string) => {
    try {
      await apiClient.updatePurchaseOrderStatus(id, status);
      toast({ title: "Status updated successfully" });
      loadPurchaseOrders();
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.message,
        variant: "destructive",
      });
    }
  };

  const handleDelete = async (id: string) => {
    if (!confirm("Are you sure you want to delete this purchase order?"))
      return;

    try {
      await apiClient.deletePurchaseOrder(id);
      toast({ title: "Purchase order deleted successfully" });
      loadPurchaseOrders();
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.message,
        variant: "destructive",
      });
    }
  };

  const handleViewOrder = async (id: string) => {
    try {
      const order = await apiClient.getPurchaseOrder(id);
      setSelectedOrder(order);
      setIsViewDialogOpen(true);
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.message,
        variant: "destructive",
      });
    }
  };

  const addItem = () => {
    setFormData({
      ...formData,
      items: [
        ...formData.items,
        { productId: "", quantity: "", unitPrice: "" },
      ],
    });
  };

  const removeItem = (index: number) => {
    const newItems = formData.items.filter((_, i) => i !== index);
    setFormData({ ...formData, items: newItems });
  };

  const updateItem = (index: number, field: string, value: string) => {
    const newItems = [...formData.items];
    newItems[index] = { ...newItems[index], [field]: value };
    setFormData({ ...formData, items: newItems });
  };

  const resetForm = () => {
    setFormData({
      supplierId: "",
      orderDate: "",
      expectedDeliveryDate: "",
      items: [{ productId: "", quantity: "", unitPrice: "" }],
    });
  };

  const getStatusBadge = (status: string) => {
    const variants: any = {
      PENDING: "default",
      APPROVED: "secondary",
      RECEIVED: "default",
      CANCELLED: "destructive",
    };
    return <Badge variant={variants[status] || "default"}>{status}</Badge>;
  };

  return (
    <div className="flex h-screen">
      <Sidebar />
      <div className="flex-1 overflow-auto">
        <div className="container mx-auto p-6">
          <div className="flex justify-between items-center mb-6">
            <h1 className="text-3xl font-bold">Purchase Orders</h1>
            <Button onClick={() => setIsAddDialogOpen(true)}>
              <Plus className="mr-2 h-4 w-4" /> Create Order
            </Button>
          </div>

          <Card>
            <CardHeader>
              <CardTitle>Purchase Order List</CardTitle>
            </CardHeader>
            <CardContent>
              {loading ? (
                <div className="text-center py-8">Loading...</div>
              ) : (
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Order #</TableHead>
                      <TableHead>Supplier</TableHead>
                      <TableHead>Date</TableHead>
                      <TableHead>Total</TableHead>
                      <TableHead>Status</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {purchaseOrders.map((order) => (
                      <TableRow key={order.id}>
                        <TableCell className="font-medium">
                          {order.orderNumber}
                        </TableCell>
                        <TableCell>{order.supplierName}</TableCell>
                        <TableCell>
                          {new Date(order.orderDate).toLocaleDateString()}
                        </TableCell>
                        <TableCell>{formatINR(order.totalAmount)}</TableCell>
                        <TableCell>
                          <Select
                            value={order.status}
                            onValueChange={(value) =>
                              handleStatusChange(order.id, value)
                            }
                          >
                            <SelectTrigger className="w-[130px]">
                              <SelectValue />
                            </SelectTrigger>
                            <SelectContent>
                              <SelectItem value="PENDING">Pending</SelectItem>
                              <SelectItem value="APPROVED">Approved</SelectItem>
                              <SelectItem value="RECEIVED">Received</SelectItem>
                              <SelectItem value="CANCELLED">
                                Cancelled
                              </SelectItem>
                            </SelectContent>
                          </Select>
                        </TableCell>
                        <TableCell>
                          <div className="flex gap-2">
                            <Button
                              variant="ghost"
                              size="icon"
                              onClick={() => handleViewOrder(order.id)}
                            >
                              <Eye className="h-4 w-4" />
                            </Button>
                            <Button
                              variant="ghost"
                              size="icon"
                              onClick={() => handleDelete(order.id)}
                            >
                              <Trash2 className="h-4 w-4" />
                            </Button>
                          </div>
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

      <PurchaseOrderDialog
        isAddDialogOpen={isAddDialogOpen}
        setIsAddDialogOpen={setIsAddDialogOpen}
        formData={formData}
        setFormData={setFormData}
        suppliers={suppliers}
        products={products}
        handleSubmit={handleSubmit}
        addItem={addItem}
        removeItem={removeItem}
        updateItem={updateItem}
        resetForm={resetForm}
      />
      <ViewOrderDialog
        isViewDialogOpen={isViewDialogOpen}
        setIsViewDialogOpen={setIsViewDialogOpen}
        selectedOrder={selectedOrder}
        getStatusBadge={getStatusBadge}
      />
    </div>
  );
}
