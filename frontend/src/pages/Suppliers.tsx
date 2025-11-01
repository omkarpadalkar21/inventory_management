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
import { Textarea } from "@/components/ui/textarea";
import { Plus, Edit, Trash2 } from "lucide-react";
import { apiClient } from "@/lib/api";
import { useToast } from "@/hooks/use-toast";

const SupplierDialog = ({
  open,
  onOpenChange,
  isEdit,
  formData,
  setFormData,
  handleSubmit,
  resetForm,
}: any) => (
  <Dialog open={open} onOpenChange={onOpenChange}>
    <DialogContent className="max-w-2xl">
      <DialogHeader>
        <DialogTitle>
          {isEdit ? "Edit Supplier" : "Add New Supplier"}
        </DialogTitle>
        <DialogDescription>
          {isEdit ? "Update supplier details" : "Enter supplier details"}
        </DialogDescription>
      </DialogHeader>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="grid grid-cols-2 gap-4">
          <div>
            <Label htmlFor="name">Name *</Label>
            <Input
              id="name"
              value={formData.name}
              onChange={(e) =>
                setFormData({ ...formData, name: e.target.value })
              }
              required
              minLength={3}
              maxLength={50}
            />
          </div>
          <div>
            <Label htmlFor="contactPerson">Contact Person *</Label>
            <Input
              id="contactPerson"
              value={formData.contactPerson}
              onChange={(e) =>
                setFormData({ ...formData, contactPerson: e.target.value })
              }
              required
              minLength={2}
              maxLength={50}
            />
          </div>
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <Label htmlFor="email">Email *</Label>
            <Input
              id="email"
              type="email"
              value={formData.email}
              onChange={(e) =>
                setFormData({ ...formData, email: e.target.value })
              }
              required
              maxLength={100}
            />
          </div>
          <div>
            <Label htmlFor="phone">Phone *</Label>
            <Input
              id="phone"
              value={formData.phone}
              onChange={(e) =>
                setFormData({ ...formData, phone: e.target.value })
              }
              required
              maxLength={20}
            />
          </div>
        </div>

        <div>
          <Label htmlFor="address">Address</Label>
          <Textarea
            id="address"
            value={formData.address}
            onChange={(e) =>
              setFormData({ ...formData, address: e.target.value })
            }
            rows={3}
            minLength={10}
            maxLength={255}
          />
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

export default function Suppliers() {
  const [suppliers, setSuppliers] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [isAddDialogOpen, setIsAddDialogOpen] = useState(false);
  const [isEditDialogOpen, setIsEditDialogOpen] = useState(false);
  const [editingSupplier, setEditingSupplier] = useState<any>(null);
  const { toast } = useToast();

  const [formData, setFormData] = useState({
    name: "",
    contactPerson: "",
    email: "",
    phone: "",
    address: "",
  });

  useEffect(() => {
    loadSuppliers();
  }, []);

  const loadSuppliers = async () => {
    try {
      setLoading(true);
      const data = await apiClient.getSuppliers();
      setSuppliers(data);
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

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editingSupplier) {
        await apiClient.updateSupplier(editingSupplier.id, formData);
        toast({ title: "Supplier updated successfully" });
        setIsEditDialogOpen(false);
      } else {
        await apiClient.createSupplier(formData);
        toast({ title: "Supplier created successfully" });
        setIsAddDialogOpen(false);
      }
      loadSuppliers();
      resetForm();
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.message,
        variant: "destructive",
      });
    }
  };

  const handleEdit = (supplier: any) => {
    setEditingSupplier(supplier);
    setFormData({
      name: supplier.name,
      contactPerson: supplier.contactPerson,
      email: supplier.email,
      phone: supplier.phone,
      address: supplier.address || "",
    });
    setIsEditDialogOpen(true);
  };

  const handleDelete = async (id: string) => {
    if (!confirm("Are you sure you want to delete this supplier?")) return;

    try {
      await apiClient.deleteSupplier(id);
      toast({ title: "Supplier deleted successfully" });
      loadSuppliers();
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
      name: "",
      contactPerson: "",
      email: "",
      phone: "",
      address: "",
    });
    setEditingSupplier(null);
  };

  return (
    <div className="flex h-screen">
      <Sidebar />
      <div className="flex-1 overflow-auto">
        <div className="container mx-auto p-6">
          <div className="flex justify-between items-center mb-6">
            <h1 className="text-3xl font-bold">Suppliers</h1>
            <Button onClick={() => setIsAddDialogOpen(true)}>
              <Plus className="mr-2 h-4 w-4" /> Add Supplier
            </Button>
          </div>

          <Card>
            <CardHeader>
              <CardTitle>Supplier List</CardTitle>
            </CardHeader>
            <CardContent>
              {loading ? (
                <div className="text-center py-8">Loading...</div>
              ) : (
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Name</TableHead>
                      <TableHead>Contact Person</TableHead>
                      <TableHead>Email</TableHead>
                      <TableHead>Phone</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {suppliers.map((supplier) => (
                      <TableRow key={supplier.id}>
                        <TableCell className="font-medium">
                          {supplier.name}
                        </TableCell>
                        <TableCell>{supplier.contactPerson}</TableCell>
                        <TableCell>{supplier.email}</TableCell>
                        <TableCell>{supplier.phone}</TableCell>
                        <TableCell>
                          <div className="flex gap-2">
                            <Button
                              variant="ghost"
                              size="icon"
                              onClick={() => handleEdit(supplier)}
                            >
                              <Edit className="h-4 w-4" />
                            </Button>
                            <Button
                              variant="ghost"
                              size="icon"
                              onClick={() => handleDelete(supplier.id)}
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

      <SupplierDialog
        open={isAddDialogOpen}
        onOpenChange={setIsAddDialogOpen}
        isEdit={false}
        formData={formData}
        setFormData={setFormData}
        handleSubmit={handleSubmit}
        resetForm={resetForm}
      />
      <SupplierDialog
        open={isEditDialogOpen}
        onOpenChange={setIsEditDialogOpen}
        isEdit={true}
        formData={formData}
        setFormData={setFormData}
        handleSubmit={handleSubmit}
        resetForm={resetForm}
      />
    </div>
  );
}
