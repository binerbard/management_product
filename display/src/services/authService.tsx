// import { ApiURI } from "@/utils/constrain";
import { progressAlert } from "@/utils/alert";
import { ApiURI } from "@/utils/constrain";
import axios from "axios";

const apiClient = axios.create({
  baseURL: ApiURI,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

export const login = async (email: string, password: string) => {
  try {
    const response = await apiClient.post("/api/public/auth/login", {
      username: email,
      password: password,
    });

    if (response.status !== 202) {
      progressAlert("Something went wrong, please try again", false);
      throw new Error("Something went wrong, please try again");
    }

    return response.data;
  } catch (error) {
    progressAlert("Something went wrong, please try again", false);
    throw new Error("Something went wrong, please try again");
  }
};

export const logout = async () => {
  try {
    const response = await apiClient.post("/api/public/auth/logout");

    if (response.status !== 202) {
      throw new Error("Something went wrong, please try again");
    }

    return response.data;
  } catch (error) {
    throw new Error("Something went wrong, please try again");
  }
};
