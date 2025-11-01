import { useEffect, useState } from 'react';
import { Sidebar } from '@/components/Sidebar';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Package, TrendingDown, ShoppingCart, DollarSign } from 'lucide-react';
import { apiClient } from '@/lib/api';

export default function Dashboard() {
  const [stats, setStats] = useState({
    totalProducts: 0,
    lowStockItems: 0,
    pendingOrders: 0,
    totalValue: 0,
  });

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const [products, lowStock] = await Promise.all([
          apiClient.getProducts(1, 1000),
          apiClient.getLowStockProducts(),
        ]);
        
        setStats({
          totalProducts: products.total || products.data?.length || 0,
          lowStockItems: lowStock.data?.length || 0,
          pendingOrders: 0, // Would need to fetch from purchase orders
          totalValue: 0, // Would need to calculate from products
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
      value: `$${stats.totalValue.toLocaleString()}`,
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
          <h1 className="text-3xl font-bold mb-8">Dashboard</h1>
          
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
