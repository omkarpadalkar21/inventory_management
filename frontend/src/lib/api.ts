const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || "";

class ApiClient {
  private baseUrl: string;
  private token: string | null = null;

  constructor(baseUrl: string) {
    this.baseUrl = baseUrl;
    this.token = localStorage.getItem("auth_token");
  }

  setToken(token: string | null) {
    this.token = token;
    if (token) {
      localStorage.setItem("auth_token", token);
    } else {
      localStorage.removeItem("auth_token");
    }
  }

  private async request<T>(
    endpoint: string,
    options: RequestInit = {}
  ): Promise<T> {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
      ...options.headers,
    };

    if (this.token) {
      headers["Authorization"] = `Bearer ${this.token}`;
    }

    const response = await fetch(`${this.baseUrl}${endpoint}`, {
      ...options,
      headers,
    });

    if (!response.ok) {
      const error = await response
        .json()
        .catch(() => ({ message: "An error occurred" }));
      throw new Error(error.message || `HTTP ${response.status}`);
    }

    return response.json();
  }

  // Auth endpoints
  async register(data: {
    username: string;
    email: string;
    password: string;
    role: string;
  }) {
    return this.request("/api/auth/register", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  async login(data: { email: string; password: string }) {
    const response = await this.request<{ token: string; user: any }>(
      "/api/auth/login",
      {
        method: "POST",
        body: JSON.stringify(data),
      }
    );
    this.setToken(response.token);
    return response;
  }

  async logout() {
    await this.request("/api/auth/logout", { method: "POST" });
    this.setToken(null);
  }

  // Products
  async getProducts(
    page = 1,
    limit = 10
  ): Promise<{ data: any[]; total?: number }> {
    return this.request(`/api/products?page=${page}&limit=${limit}`);
  }

  async getProduct(id: number) {
    return this.request(`/api/products/${id}`);
  }

  async getLowStockProducts(): Promise<{ data: any[] }> {
    return this.request("/api/products/low-stock");
  }

  async searchProducts(keyword: string): Promise<{ data: any[] }> {
    return this.request(
      `/api/products/search?keyword=${encodeURIComponent(keyword)}`
    );
  }

  async createProduct(data: any) {
    return this.request("/api/products", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  async updateProduct(id: number, data: any) {
    return this.request(`/api/products/${id}`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  }

  async deleteProduct(id: number) {
    return this.request(`/api/products/${id}`, { method: "DELETE" });
  }

  // Categories
  async getCategories(): Promise<{ data: any[] }> {
    return this.request("/api/categories");
  }

  async getCategory(id: number) {
    return this.request(`/api/categories/${id}`);
  }

  async createCategory(data: any) {
    return this.request("/api/categories", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  async updateCategory(id: number, data: any) {
    return this.request(`/api/categories/${id}`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  }

  async deleteCategory(id: number) {
    return this.request(`/api/categories/${id}`, { method: "DELETE" });
  }

  // Suppliers
  async getSuppliers(): Promise<{ data: any[] }> {
    return this.request("/api/suppliers");
  }

  async getSupplier(id: number) {
    return this.request(`/api/suppliers/${id}`);
  }

  async createSupplier(data: any) {
    return this.request("/api/suppliers", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  async updateSupplier(id: number, data: any) {
    return this.request(`/api/suppliers/${id}`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  }

  async deleteSupplier(id: number) {
    return this.request(`/api/suppliers/${id}`, { method: "DELETE" });
  }

  // Stock Transactions
  async getStockTransactions(): Promise<{ data: any[] }> {
    return this.request("/api/stock-transactions");
  }

  async getStockTransaction(id: number) {
    return this.request(`/api/stock-transactions/${id}`);
  }

  async getProductTransactionHistory(productId: number) {
    return this.request(`/api/stock-transactions/product/${productId}`);
  }

  async stockIn(data: any) {
    return this.request("/api/stock-transactions/stock-in", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  async stockOut(data: any) {
    return this.request("/api/stock-transactions/stock-out", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  async adjustStock(data: any) {
    return this.request("/api/stock-transactions/adjust", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  // Purchase Orders
  async getPurchaseOrders(): Promise<{ data: any[] }> {
    return this.request("/api/purchase-orders");
  }

  async getPurchaseOrder(id: number) {
    return this.request(`/api/purchase-orders/${id}`);
  }

  async createPurchaseOrder(data: any) {
    return this.request("/api/purchase-orders", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  async updatePurchaseOrder(id: number, data: any) {
    return this.request(`/api/purchase-orders/${id}`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  }

  async updatePurchaseOrderStatus(id: number, status: string) {
    return this.request(`/api/purchase-orders/${id}/status`, {
      method: "PATCH",
      body: JSON.stringify({ status }),
    });
  }

  async deletePurchaseOrder(id: number) {
    return this.request(`/api/purchase-orders/${id}`, { method: "DELETE" });
  }

  // Users (Admin only)
  async getUsers(): Promise<{ data: any[] }> {
    return this.request("/api/users");
  }

  async getUser(id: number) {
    return this.request(`/api/users/${id}`);
  }

  async updateUser(id: number, data: any) {
    return this.request(`/api/users/${id}`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  }

  async deleteUser(id: number) {
    return this.request(`/api/users/${id}`, { method: "DELETE" });
  }
}

export const apiClient = new ApiClient(API_BASE_URL);
