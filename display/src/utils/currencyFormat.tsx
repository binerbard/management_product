export const formatCurrency = (amount: number): string => {
  return new Intl.NumberFormat("id-ID", {
    style: "currency",
    currency: "IDR", // Ganti dengan mata uang yang diinginkan, misal 'USD' untuk dolar AS
  }).format(amount);
};
