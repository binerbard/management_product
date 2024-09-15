"use client";

import { CardButton } from "@/components/button/card";
import {
  Product,
  ProductResponse,
} from "@/datasources/domain/interface/product";
import { listProduct } from "@/services/listProductService";
import { useCheckLogin } from "@/utils/checker";
import { formatCurrency } from "@/utils/currencyFormat";
import React, { useEffect, useState } from "react";
import { UpdateProduct } from "./update";
import { DeleteProduct } from "./delete";
import { AddProduct } from "./add";
import { ApproveProduct } from "./approve";
import { RejectProduct } from "./reject";

interface ListProductProps {
  search: string;
}

export const ListProduct: React.FC<ListProductProps> = ({ search }) => {
  const [products, setProducts] = useState<Product[]>([]);
  const [status, setStatus] = useState<"pending" | "approve" | "reject">(
    "pending"
  );
  const { checkLogin } = useCheckLogin();
  const handleAction = () => {
    return localStorage.getItem("role") === "ACCOUNT" ? false : true;
  };

  const fetchData = async () => {
    const response: ProductResponse = await listProduct(search, status);
    checkLogin(response.code);
    if (response.data.content != null) {
      setProducts(response.data.content);
    }
  };

  useEffect(() => {
    fetchData();
  }, [search, status]);

  return (
    <section className="list-product">
      <div className="button-action">
        <AddProduct onLoadData={fetchData}></AddProduct>
      </div>
      <div className="container mx-auto">
        <div className="action-button flex gap-2 my-2 border-2 border-blue-300 p-2 rounded-lg">
          <CardButton
            size="7rem"
            onClick={() => setStatus("pending")}
            title="Pending"
          ></CardButton>
          <CardButton
            size="7rem"
            onClick={() => setStatus("approve")}
            title="Approve"
          ></CardButton>
          <CardButton
            size="7rem"
            onClick={() => setStatus("reject")}
            title="Reject"
          ></CardButton>
        </div>
        <div className="list-data">
          {products.length > 0 ? (
            products.map((product) => (
              <div className="listing" key={product.id}>
                <div className="card border-2 border-gray-300 p-3 my-3 rounded-lg flex justify-between">
                  <div className="header">
                    <div className="status bg-blue-500 text-white p-1 w-fit rounded my-2">
                      <p>{product.status_title}</p>
                    </div>
                    <div className="title font-medium">
                      <h1>{product.name}</h1>
                    </div>
                    <div className="price text-gray-600">
                      <h1>{formatCurrency(product.price)}</h1>
                    </div>
                  </div>
                  <div className="action flex justify-normal items-center gap-2">
                    {handleAction() ? (
                      <>
                        <div className="button-action">
                          <ApproveProduct
                            product={product}
                            onLoadData={fetchData}
                          ></ApproveProduct>
                        </div>
                        <div className="button-action">
                          <RejectProduct
                            product={product}
                            onLoadData={fetchData}
                          ></RejectProduct>
                        </div>
                      </>
                    ) : (
                      ""
                    )}
                    <div className="button-action">
                      <UpdateProduct
                        product={product}
                        onLoadData={fetchData}
                      ></UpdateProduct>
                    </div>
                    <div className="button-action">
                      <DeleteProduct
                        onLoadData={fetchData}
                        product={product}
                      ></DeleteProduct>
                    </div>
                  </div>
                </div>
              </div>
            ))
          ) : (
            <div className="text-center">No products found</div>
          )}
        </div>
      </div>
    </section>
  );
};
