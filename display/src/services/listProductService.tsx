import { ApiURI } from "@/utils/constrain";
import axios from "axios";

export const listProduct = async (
  search: string,
  status: string = "pending"
) => {
  const token = localStorage.getItem("token");
  const path = localStorage.getItem("role") === "ACCOUNT" ? "user" : "admin";
  const apiClient = axios.create({
    baseURL: ApiURI,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    withCredentials: true,
  });

  try {
    const response = await apiClient.get(`/api/${path}/product/list/status`, {
      params: { search: search, status: status },
    });

    if (response.status !== 202) {
      throw new Error("Something went wrong, please try again");
    }
    return response.data;
  } catch (error) {
    throw new Error("Something went wrong, please try again");
  }
};
