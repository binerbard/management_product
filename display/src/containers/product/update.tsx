import { AcctionButton } from "@/components/button/action";
import BottomSheet from "@/components/decoration/buttomsheet";
import {
  Product,
  ProductFormValues,
} from "@/datasources/domain/interface/product";
import { ResponseStatus } from "@/datasources/domain/interface/responseStatus";
import { ProductSchema } from "@/datasources/domain/schema/product";
import { updateProduct } from "@/services/updateProductService";
import { progressAlert } from "@/utils/alert";
import { useCheckLogin } from "@/utils/checker";
import { yupResolver } from "@hookform/resolvers/yup";
import React, { useState } from "react";
import { SubmitHandler, useForm } from "react-hook-form";

interface UpdateProductProps {
  product: Product;
  onLoadData: () => void;
}

const productSchema = ProductSchema;

export const UpdateProduct: React.FC<UpdateProductProps> = ({
  product,
  onLoadData,
}) => {
  const [isSheetOpen, setIsSheetOpen] = useState(false);
  const { checkLogin } = useCheckLogin();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<ProductFormValues>({
    resolver: yupResolver(productSchema),
    defaultValues: {
      product: product.name,
      description: product.description,
      price: product.price,
    },
  });

  const handleAction = async () => {
    setIsSheetOpen(!isSheetOpen);
  };

  const onSubmit: SubmitHandler<ProductFormValues> = async (data) => {
    try {
      const response: ResponseStatus = await updateProduct(
        data.product,
        data.description,
        data.price,
        product.id
      );
      checkLogin(response.code);

      progressAlert(`${response.message}`, true);
      onLoadData();
      handleAction();
    } catch (error) {
      progressAlert("Something went wrong, please try again", false);
      throw new Error("Something went wrong, please try again");
    }
  };
  return (
    <section className="update-product" id="update-product">
      <AcctionButton onClick={handleAction} icon="ri-edit-line"></AcctionButton>
      <BottomSheet isOpen={isSheetOpen} onClose={handleAction} height="80vh">
        <div className="title">
          <h2 className="text-xl font-bold mb-4">Update Product</h2>
        </div>
        <div className="container mx-auto">
          <div className="body">
            <form
              action=""
              onSubmit={handleSubmit(onSubmit)}
              className="flex flex-col gap-3"
            >
              <div id="product">
                <input
                  type="text"
                  {...register("product")}
                  placeholder="Product Name"
                  required
                  className="w-full border-2 border-blue-400 p-4 rounded-xl"
                />
                {errors.product && (
                  <p className="text-red-500 my-1 mx-2">
                    {errors.product.message}
                  </p>
                )}
              </div>
              <div id="description">
                <textarea
                  {...register("description")}
                  required
                  className="w-full border-2 border-blue-400 rounded-xl h-[10rem] p-4"
                  placeholder="Product Description"
                ></textarea>
                {errors.description && (
                  <p className="text-red-500 my-1 mx-2">
                    {errors.description.message}
                  </p>
                )}
              </div>

              <div id="price">
                <input
                  type="number"
                  {...register("price")}
                  placeholder="Product Price"
                  required
                  min={1}
                  className="w-full border-2 border-blue-400 p-4 rounded-xl"
                />
                {errors.price && (
                  <p className="text-red-500 my-1 mx-2">
                    {errors.price.message}
                  </p>
                )}
              </div>
              <button className="w-full font-medium bg-blue-500 p-4 rounded-xl text-white">
                Save Product
              </button>
            </form>
          </div>
        </div>
      </BottomSheet>
    </section>
  );
};
