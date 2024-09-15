import { progressAlert } from "@/utils/alert";
import { ApiURI } from "@/utils/constrain";
import axios from "axios";

export const approveProduct = async (id: string) => {
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
    const response = await apiClient.put(`/api/${path}/product/${id}/approve`);
    return response.data;
  } catch (error) {
    progressAlert("Something went wrong, please try again", false);
    throw new Error("Something went wrong, please try again");
  }
};
