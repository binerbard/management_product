// Definisikan tipe data untuk form
export interface LoginFormValues {
  email: string;
  password: string;
}

// Definisikan tipe data untuk respons API
export interface TokenResponse {
  token: string;
  role: string;
}

export interface LoginResponse {
  code: number;
  message: string;
  data: TokenResponse;
}
