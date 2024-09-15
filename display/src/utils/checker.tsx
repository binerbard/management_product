import { useRouter } from "next/navigation";
import { progressAlert } from "./alert";

export const useCheckLogin = () => {
  const router = useRouter();
  const checkLogin = (statusCode: number) => {
    if (statusCode === 401 || statusCode === 403) {
      progressAlert("Please login again", false);
      router.push("/");
    } else if (statusCode !== 202) {
      progressAlert("Something went wrong, please try again", false);
      throw new Error("Something went wrong, please try again");
    }
  };
  return { checkLogin };
};
