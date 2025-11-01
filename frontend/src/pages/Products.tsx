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
  DialogTrigger,
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
import { Plus, Search, Edit, Trash2, AlertCircle } from "lucide-react";
import { apiClient } from "@/lib/api";
import { useToast } from "@/hooks/use-toast";
import { Badge } from "@/components/ui/badge";

const ProductDialog = ({
  open,
  onOpenChange,
  isEdit,
  formData,
  setFormData,
  categories,
  suppliers,
  handleSubmit,
  resetForm,
}: any) => (
  <Dialog open={open} onOpenChange={onOpenChange}>
    <DialogContent className="max-w-2xl max-h-[90vh] overflow-y-auto">
      <DialogHeader>
        <DialogTitle>
          {isEdit ? "Edit Product" : "Add New Product"}
        </DialogTitle>
        <DialogDescription>
          {isEdit ? "Update product details" : "Enter product details"}
        </DialogDescription>
      </DialogHeader>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="grid grid-cols-2 gap-4">
          <div>
            <Label htmlFor="sku">SKU *</Label>
            <Input
              id="sku"
              value={formData.sku}
              onChange={(e) => setFormData({ ...formData, sku: e.target.value })}
              required
              maxLength={50}
            />
          </div>
          <div>
            <Label htmlFor="name">Name *</Label>
            <Input
              id="name"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              required
              maxLength={200}
            />
          </div>
        </div>

        <div>
          <Label htmlFor="description">Description</Label>
          <Textarea
            id="description"
            value={formData.description}
            onChange={(e) => setFormData({ ...formData, description: e.target.value })}
            rows={3}
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <Label htmlFor="category">Category *</Label>
            <Select
              value={formData.categoryId}
              onValueChange={(value) => setFormData({ ...formData, categoryId: value })}
              required
            >
              <SelectTrigger>
                <SelectValue placeholder="Select category" />
              </SelectTrigger>
              <SelectContent>
                {categories.map((cat: any) => (
                  <SelectItem key={cat.id} value={cat.id}>
                    {cat.name}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
          <div>
            <Label htmlFor="supplier">Supplier</Label>
            <Select
              value={formData.supplierId}
              onValueChange={(value) => setFormData({ ...formData, supplierId: value })}
            >
              <SelectTrigger>
                <SelectValue placeholder="Select supplier" />
              </SelectTrigger>
              <SelectContent>
                {suppliers.map((sup: any) => (
                  <SelectItem key={sup.id} value={sup.id}>
                    {sup.name}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <Label htmlFor="unitPrice">Unit Price *</Label>
            <Input
              id="unitPrice"
              type="number"
              step="0.01"
              min="0"
              value={formData.unitPrice}
              onChange={(e) => setFormData({ ...formData, unitPrice: e.target.value })}
              required
            />
          </div>
          <div>
            <Label htmlFor="costPrice">Cost Price</Label>
            <Input
              id="costPrice"
              type="number"
              step="0.01"
              min="0"
              value={formData.costPrice}
              onChange={(e) => setFormData({ ...formData, costPrice: e.target.value })}
            />
          </div>
        </div>

        <div className="grid grid-cols-3 gap-4">
          <div>
            <Label htmlFor="quantity">Quantity</Label>
            <Input
              id="quantity"
              type="number"
              min="0"
              value={formData.quantityInStock}
              onChange={(e) => setFormData({ ...formData, quantityInStock: e.target.value })}
            />
          </div>
          <div>
            <Label htmlFor="reorder">Reorder Level</Label>
            <Input
              id="reorder"
              type="number"
              min="0"
              value={formData.reorderLevel}
              onChange={(e) => setFormData({ ...formData, reorderLevel: e.target.value })}
            />
          </div>
          <div>
            <Label htmlFor="status">Status</Label>
            <Select
              value={formData.status}
              onValueChange={(value) => setFormData({ ...formData, status: value })}
            >
              <SelectTrigger>
                <SelectValue />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="ACTIVE">Active</SelectItem>
                <SelectItem value="INACTIVE">Inactive</SelectItem>
                <SelectItem value="DISCONTINUED">Discontinued</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </div>

        <DialogFooter>
          <Button
            type="button"
            variant="outline"
            onClick={() => {
              onOpenChange(false);
              resetForm();
            }}
          >
            Cancel
          </Button>
          <Button type="submit">{isEdit ? "Update" : "Create"}</Button>
        </DialogFooter>
      </form>
    </DialogContent>
  </Dialog>
);

export default function Products() {
  const [products, setProducts] = useState<any[]>([]);
  const [categories, setCategories] = useState<any[]>([]);
  const [suppliers, setSuppliers] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState("");
  const [isAddDialogOpen, setIsAddDialogOpen] = useState(false);
  const [isEditDialogOpen, setIsEditDialogOpen] = useState(false);
  const [editingProduct, setEditingProduct] = useState<any>(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [isSearching, setIsSearching] = useState(false);
  const { toast } = useToast();

  const [formData, setFormData] = useState({
    sku: "",
    name: "",
    description: "",
    categoryId: "",
    supplierId: "",
    unitPrice: "",
    costPrice: "",
    quantityInStock: "0",
    reorderLevel: "10",
    status: "ACTIVE",
  });

  useEffect(() => {
    if (!isSearching) {
      loadProducts();
    }
  }, [page, isSearching]);

  useEffect(() => {
    loadCategories();
    loadSuppliers();
  }, []);

  const loadProducts = async () => {
    try {
      setLoading(true);
      const response = await apiClient.getProducts(page, 10);
      setProducts(response.data);
      setTotalPages(response.totalPages);
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

  const loadCategories = async () => {
    try {
      const data = await apiClient.getCategories();
      setCategories(data);
    } catch (error: any) {
      toast({
        title: "Error loading categories",
        description: error.message,
        variant: "destructive",
      });
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

  const handleSearch = async () => {
    if (!searchTerm.trim()) {
      setIsSearching(false);
      setPage(0);
      await loadProducts();
      return;
    }
    try {
      setLoading(true);
      setIsSearching(true);
      const data = await apiClient.searchProducts(searchTerm);
      setProducts(data);
      setPage(0);
      setTotalPages(1);
    } catch (error: any) {
      toast({
        title: "Search error",
        description: error.message,
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const payload = {
        ...formData,
        unitPrice: parseFloat(formData.unitPrice),
        costPrice: formData.costPrice
          ? parseFloat(formData.costPrice)
          : undefined,
        quantityInStock: parseInt(formData.quantityInStock),
        reorderLevel: parseInt(formData.reorderLevel),
        supplierId: formData.supplierId || undefined,
      };

      if (editingProduct) {
        await apiClient.updateProduct(editingProduct.id, payload);
        toast({ title: "Product updated successfully" });
        setIsEditDialogOpen(false);
      } else {
        await apiClient.createProduct(payload);
        toast({ title: "Product created successfully" });
        setIsAddDialogOpen(false);
      }

      loadProducts();
      resetForm();
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.message,
        variant: "destructive",
      });
    }
  };

  const handleEdit = (product: any) => {
    setEditingProduct(product);
    setFormData({
      sku: product.sku,
      name: product.name,
      description: product.description || "",
      categoryId: product.categoryId,
      supplierId: product.supplierId || "",
      unitPrice: product.unitPrice.toString(),
      costPrice: product.costPrice?.toString() || "",
      quantityInStock: product.quantityInStock.toString(),
      reorderLevel: product.reorderLevel.toString(),
      status: product.status,
    });
    setIsEditDialogOpen(true);
  };

  const handleDelete = async (id: string) => {
    if (!confirm("Are you sure you want to delete this product?")) return;

    try {
      await apiClient.deleteProduct(id);
      toast({ title: "Product deleted successfully" });
      loadProducts();
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
      sku: "",
      name: "",
      description: "",
      categoryId: "",
      supplierId: "",
      unitPrice: "",
      costPrice: "",
      quantityInStock: "0",
      reorderLevel: "10",
      status: "ACTIVE",
    });
    setEditingProduct(null);
  };
  return (
    <div className="flex h-screen">
      <Sidebar />
      <div className="flex-1 overflow-auto">
        <div className="container mx-auto p-6">
          <div className="flex justify-between items-center mb-6">
            <h1 className="text-3xl font-bold">Products</h1>
            <Button onClick={() => setIsAddDialogOpen(true)}>
              <Plus className="mr-2 h-4 w-4" /> Add Product
            </Button>
          </div>

          <Card className="mb-6">
            <CardContent className="pt-6">
              <div className="flex gap-2">
                <Input
                  placeholder="Search by name or SKU..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  onKeyDown={(e) => e.key === "Enter" && handleSearch()}
                  className="flex-1"
                />
                <Button onClick={handleSearch}>
                  <Search className="h-4 w-4" />
                </Button>
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>Product List</CardTitle>
            </CardHeader>
            <CardContent>
              {loading ? (
                <div className="text-center py-8">Loading...</div>
              ) : (
                <>
                  <Table>
                    <TableHeader>
                      <TableRow>
                        <TableHead>SKU</TableHead>
                        <TableHead>Name</TableHead>
                        <TableHead>Category</TableHead>
                        <TableHead>Price</TableHead>
                        <TableHead>Stock</TableHead>
                        <TableHead>Status</TableHead>
                        <TableHead>Actions</TableHead>
                      </TableRow>
                    </TableHeader>
                    <TableBody>
                      {products.map((product) => (
                        <TableRow key={product.id}>
                          <TableCell className="font-medium">
                            {product.sku}
                          </TableCell>
                          <TableCell>{product.name}</TableCell>
                          <TableCell>{product.categoryName}</TableCell>
                          <TableCell>${product.unitPrice}</TableCell>
                          <TableCell>
                            {product.quantityInStock <= product.reorderLevel ? (
                              <Badge
                                variant="destructive"
                                className="flex items-center gap-1"
                              >
                                <AlertCircle className="h-3 w-3" />
                                {product.quantityInStock}
                              </Badge>
                            ) : (
                              product.quantityInStock
                            )}
                          </TableCell>
                          <TableCell>
                            <Badge
                              variant={
                                product.status === "ACTIVE"
                                  ? "default"
                                  : product.status === "INACTIVE"
                                  ? "secondary"
                                  : "destructive"
                              }
                            >
                              {product.status}
                            </Badge>
                          </TableCell>
                          <TableCell>
                            <div className="flex gap-2">
                              <Button
                                variant="ghost"
                                size="icon"
                                onClick={() => handleEdit(product)}
                              >
                                <Edit className="h-4 w-4" />
                              </Button>
                              <Button
                                variant="ghost"
                                size="icon"
                                onClick={() => handleDelete(product.id)}
                              >
                                <Trash2 className="h-4 w-4" />
                              </Button>
                            </div>
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>

                  <div className="flex justify-between items-center mt-4">
                    <Button
                      onClick={() => setPage(Math.max(0, page - 1))}
                      disabled={page === 0 || isSearching}
                    >
                      Previous
                    </Button>
                    <span>
                      Page {page + 1} of {totalPages}
                    </span>
                    <Button
                      onClick={() => setPage(page + 1)}
                      disabled={page + 1 >= totalPages || isSearching}
                    >
                      Next
                    </Button>
                  </div>
                </>
              )}
            </CardContent>
          </Card>
        </div>
      </div>

      <ProductDialog
        open={isAddDialogOpen}
        onOpenChange={setIsAddDialogOpen}
        isEdit={false}
        formData={formData}
        setFormData={setFormData}
        categories={categories}
        suppliers={suppliers}
        handleSubmit={handleSubmit}
        resetForm={resetForm}
      />
      <ProductDialog
        open={isEditDialogOpen}
        onOpenChange={setIsEditDialogOpen}
        isEdit={true}
        formData={formData}
        setFormData={setFormData}
        categories={categories}
        suppliers={suppliers}
        handleSubmit={handleSubmit}
        resetForm={resetForm}
      />
    </div>
  );
}
