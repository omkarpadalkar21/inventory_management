import { Link, useLocation } from "react-router-dom";
import {
  LayoutDashboard,
  Package,
  FolderTree,
  Truck,
  ArrowRightLeft,
  ShoppingCart,
  Users,
  LogOut,
} from "lucide-react";
import { useAuth } from "@/contexts/AuthContext";
import { Button } from "@/components/ui/button";

export const Sidebar = () => {
  const location = useLocation();
  const { user, logout } = useAuth();

  const navItems = [
    { path: "/dashboard", icon: LayoutDashboard, label: "Dashboard" },
    { path: "/products", icon: Package, label: "Products" },
    { path: "/categories", icon: FolderTree, label: "Categories" },
    { path: "/suppliers", icon: Truck, label: "Suppliers" },
    { path: "/transactions", icon: ArrowRightLeft, label: "Transactions" },
    { path: "/purchase-orders", icon: ShoppingCart, label: "Purchase Orders" },
  ];

  if (user?.role === "admin") {
    navItems.push({ path: "/users", icon: Users, label: "Users" });
  }

  return (
    <div className="w-64 bg-sidebar border-r border-sidebar-border flex flex-col ">
      <div className="p-6 border-b border-sidebar-border">
        <div className="flex items-center gap-3 pb-5">
          <div className="w-8 h-8 bg-primary rounded-lg flex items-center justify-center">
            <Package className="w-4 h-4 text-primary-foreground" />
          </div>
          <h1 className="text-2xl font-bold text-sidebar-foreground">
            LogistiQ
          </h1>
        </div>
        <p className="text-lg font-bold text-white mt-1">{user?.username}</p>
      </div>

      <nav className="flex-1 p-4 space-y-2">
        {navItems.map((item) => {
          const Icon = item.icon;
          const isActive = location.pathname === item.path;

          return (
            <Link
              key={item.path}
              to={item.path}
              className={`flex items-center gap-3 px-4 py-3 rounded-lg transition-colors ${
                isActive
                  ? "bg-sidebar-accent text-sidebar-accent-foreground"
                  : "text-sidebar-foreground hover:bg-sidebar-accent/50"
              }`}
            >
              <Icon className="w-5 h-5" />
              <span className="font-medium">{item.label}</span>
            </Link>
          );
        })}
      </nav>

      <div className="p-4 border-t border-sidebar-border">
        <Button
          variant="ghost"
          className="w-full justify-start text-sidebar-foreground hover:bg-sidebar-accent/50"
          onClick={logout}
        >
          <LogOut className="w-5 h-5 mr-3" />
          Logout
        </Button>
      </div>
    </div>
  );
};
