import { useEffect, useState } from 'react';
import { Sidebar } from '@/components/Sidebar';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Package, TrendingDown, ShoppingCart, DollarSign, LogOut } from 'lucide-react';
import { apiClient } from '@/lib/api';
import { Button } from '@/components/ui/button';
import { useAuth } from '@/contexts/AuthContext';

const formatINR = (value: number | string) =>
  new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', maximumFractionDigits: 2 }).format(Number(value ?? 0));

export default function Dashboard() {
  const [stats, setStats] = useState({
    totalProducts: 0,
    lowStockItems: 0,
    pendingOrders: 0,
    totalValue: 0,
  });
  const { logout } = useAuth();

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const [productsResponse, lowStock, purchaseOrders] = await Promise.all([
          apiClient.getProducts(0, 1000),
          apiClient.getLowStockProducts(),
          apiClient.getPurchaseOrders(),
        ]);

        const productList = (productsResponse as any)?.data ?? [];
        const totalValue = productList.reduce(
          (sum: number, p: any) => sum + (Number(p.unitPrice) || 0) * (Number(p.quantityInStock) || 0),
          0
        );
        const pendingOrders = (purchaseOrders as any[])?.filter((o: any) => o.status === 'PENDING')?.length ?? 0;

        setStats({
          totalProducts:
            (productsResponse as any)?.totalElements ?? productList.length ?? 0,
          lowStockItems: (lowStock as any[])?.length ?? 0,
          pendingOrders,
          totalValue,
        });
      } catch (error) {
        console.error('Failed to fetch stats:', error);
      }
    };

    fetchStats();
  }, []);

  const statCards = [
    {
      title: 'Total Products',
      value: stats.totalProducts,
      icon: Package,
      color: 'text-primary',
      bgColor: 'bg-primary/10',
    },
    {
      title: 'Low Stock Items',
      value: stats.lowStockItems,
      icon: TrendingDown,
      color: 'text-warning',
      bgColor: 'bg-warning/10',
    },
    {
      title: 'Pending Orders',
      value: stats.pendingOrders,
      icon: ShoppingCart,
      color: 'text-accent',
      bgColor: 'bg-accent/10',
    },
    {
      title: 'Total Value',
      value: formatINR(stats.totalValue),
      icon: DollarSign,
      color: 'text-success',
      bgColor: 'bg-success/10',
    },
  ];

  return (
    <div className="flex min-h-screen">
      <Sidebar />
      <main className="flex-1 p-8 bg-background">
        <div className="max-w-7xl mx-auto">
          <div className="flex justify-between items-center mb-8">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 bg-primary rounded-lg flex items-center justify-center">
                <Package className="w-5 h-5 text-primary-foreground" />
              </div>
              <h1 className="text-3xl font-bold">Dashboard</h1>
            </div>
            <Button variant="outline" onClick={logout}>
              <LogOut className="w-4 h-4 mr-2" /> Logout
            </Button>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
            {statCards.map((stat) => {
              const Icon = stat.icon;
              return (
                <Card key={stat.title}>
                  <CardHeader className="flex flex-row items-center justify-between pb-2">
                    <CardTitle className="text-sm font-medium text-muted-foreground">
                      {stat.title}
                    </CardTitle>
                    <div className={`w-10 h-10 ${stat.bgColor} rounded-lg flex items-center justify-center`}>
                      <Icon className={`w-5 h-5 ${stat.color}`} />
                    </div>
                  </CardHeader>
                  <CardContent>
                    <div className="text-2xl font-bold">{stat.value}</div>
                  </CardContent>
                </Card>
              );
            })}
          </div>

          <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
            <Card>
              <CardHeader>
                <CardTitle>Recent Activity</CardTitle>
              </CardHeader>
              <CardContent>
                <p className="text-muted-foreground">No recent activity to display</p>
              </CardContent>
            </Card>

            <Card>
              <CardHeader>
                <CardTitle>Quick Actions</CardTitle>
              </CardHeader>
              <CardContent className="space-y-2">
                <p className="text-muted-foreground">Use the sidebar to navigate to different sections</p>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>
    </div>
  );
}
