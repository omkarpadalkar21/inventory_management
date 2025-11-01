const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

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

    // Handle 204 No Content
    if (response.status === 204) {
      return null as T;
    }

    return response.json();
  }

  // Auth
  async register(data: {
    username: string;
    email: string;
    password: string;
    role: string;
  }) {
    return this.request<any>("/api/auth/register", {
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
  async getProducts(page = 0, size = 10) {
    return this.request<{
      data: any[];
      page: number;
      size: number;
      totalElements: number;
      totalPages: number;
    }>(`/api/products?page=${page}&size=${size}`);
  }

  async getProduct(id: string) {
    return this.request<any>(`/api/products/${id}`);
  }

  async getLowStockProducts() {
    return this.request<any[]>("/api/products/low-stock");
  }

  async searchProducts(keyword: string) {
    return this.request<any[]>(
      `/api/products/search?keyword=${encodeURIComponent(keyword)}`
    );
  }

  async createProduct(data: any) {
    return this.request<any>("/api/products", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  async updateProduct(id: string, data: any) {
    return this.request<any>(`/api/products/${id}`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  }

  async deleteProduct(id: string) {
    return this.request<void>(`/api/products/${id}`, { method: "DELETE" });
  }

  // Categories
  async getCategories() {
    return this.request<any[]>("/api/categories");
  }

  async getCategory(id: string) {
    return this.request<any>(`/api/categories/${id}`);
  }

  async createCategory(data: { name: string; description?: string }) {
    return this.request<any>("/api/categories", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  async updateCategory(
    id: string,
    data: { name?: string; description?: string }
  ) {
    return this.request<any>(`/api/categories/${id}`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  }

  async deleteCategory(id: string) {
    return this.request<void>(`/api/categories/${id}`, { method: "DELETE" });
  }

  // Suppliers
  async getSuppliers() {
    return this.request<any[]>("/api/suppliers");
  }

  async getSupplier(id: string) {
    return this.request<any>(`/api/suppliers/${id}`);
  }

  async createSupplier(data: any) {
    return this.request<any>("/api/suppliers", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  async updateSupplier(id: string, data: any) {
    return this.request<any>(`/api/suppliers/${id}`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  }

  async deleteSupplier(id: string) {
    return this.request<void>(`/api/suppliers/${id}`, { method: "DELETE" });
  }

  // Stock Transactions
  async getStockTransactions() {
    return this.request<any[]>("/api/stock-transactions");
  }

  async getStockTransaction(id: string) {
    return this.request<any>(`/api/stock-transactions/${id}`);
  }

  async getProductTransactionHistory(productId: string) {
    return this.request<any[]>(`/api/stock-transactions/product/${productId}`);
  }

  async stockIn(data: {
    productId: string;
    quantity: number;
    referenceNumber?: string;
    notes?: string;
  }) {
    return this.request<any>("/api/stock-transactions/stock-in", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  async stockOut(data: {
    productId: string;
    quantity: number;
    referenceNumber?: string;
    notes?: string;
  }) {
    return this.request<any>("/api/stock-transactions/stock-out", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  async adjustStock(data: {
    productId: string;
    quantity: number;
    referenceNumber?: string;
    notes: string;
  }) {
    return this.request<any>("/api/stock-transactions/adjust", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  // Purchase Orders
  async getPurchaseOrders() {
    return this.request<any[]>("/api/purchase-orders");
  }

  async getPurchaseOrder(id: string) {
    return this.request<any>(`/api/purchase-orders/${id}`);
  }

  async createPurchaseOrder(data: any) {
    return this.request<any>("/api/purchase-orders", {
      method: "POST",
      body: JSON.stringify(data),
    });
  }

  async updatePurchaseOrder(id: string, data: any) {
    return this.request<any>(`/api/purchase-orders/${id}`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  }

  async updatePurchaseOrderStatus(id: string, status: string) {
    return this.request<any>(`/api/purchase-orders/${id}/status`, {
      method: "PATCH",
      body: JSON.stringify({ status }),
    });
  }

  async deletePurchaseOrder(id: string) {
    return this.request<void>(`/api/purchase-orders/${id}`, {
      method: "DELETE",
    });
  }

  // Users
  async getUsers() {
    return this.request<any[]>("/api/users");
  }

  async getUser(id: string) {
    return this.request<any>(`/api/users/${id}`);
  }

  async deleteUser(id: string) {
    return this.request<void>(`/api/users/${id}`, { method: "DELETE" });
  }
}

export const apiClient = new ApiClient(API_BASE_URL);
