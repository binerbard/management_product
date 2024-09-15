// Product Values
export interface ProductFormValues {
  product: string;
  price: number;
  description: string;
}

export interface Product {
  id: string;
  name: string;
  description: string;
  price: number;
  status_title: string;
  createdAt: string;
  updatedAt: string;
}

export interface ProductResponse {
  code: number;
  message: string;
  data: {
    content: Product[];
    pageable: object;
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number;
    number: number;
    sort: object;
    numberOfElements: number;
    first: boolean;
    empty: boolean;
  };
}
