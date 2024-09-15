"use client";
import { useState } from "react";
import { ListProduct } from "./list";
import { CardButton } from "@/components/button/card";
import { useRouter } from "next/navigation";
import { logout } from "@/services/authService";
import { ResponseStatus } from "@/datasources/domain/interface/responseStatus";
import Swal from "sweetalert2";
import { progressAlert } from "@/utils/alert";

export const ProductSection: React.FC = () => {
  const [search, setSearch] = useState("");
  const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearch(e.target.value);
  };
  const router = useRouter();

  const handleAction = async () => {
    try {
      const response: ResponseStatus = await logout();

      if (response.code === 202) {
        localStorage.removeItem("token");
        localStorage.removeItem("role");
        localStorage.clear();
      }

      progressAlert(`Logout successfully`, true);
      router.push("/");
    } catch (error) {
      progressAlert("Something went wrong, please try again", false);
    }
  };

  const handleLogout = async () => {
    Swal.fire({
      title: "Are you sure?",
      text: "You want to logout?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!",
    }).then((result) => {
      if (result.isConfirmed) {
        handleAction();
      }
    });
  };
  return (
    <section className="product">
      <div className="container mx-auto">
        <div className="flex justify-center items-center">
          <div className="container border-2 border-blue-500 p-3 m-3 rounded-xl">
            <div className="title font-medium text-lg my-4 flex justify-between">
              <div className="title">
                <h1>Product Management</h1>
              </div>
              <div className="logout">
                <CardButton
                  onClick={handleLogout}
                  size="7rem"
                  title="Logout"
                ></CardButton>
              </div>
            </div>
            <div className="body my-4">
              <div className="search-bar">
                <input
                  type="text"
                  placeholder="Search"
                  className="w-full p-4 rounded-xl border-2 border-blue"
                  value={search}
                  onChange={handleSearch}
                />
              </div>
              <div className="container my-3 border-2 border-blue-600 p-2 rounded-lg">
                <ListProduct search={search}></ListProduct>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
