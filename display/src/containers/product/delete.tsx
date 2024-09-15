import { AcctionButton } from "@/components/button/action";
import BottomSheet from "@/components/decoration/buttomsheet";
import { Product } from "@/datasources/domain/interface/product";
import { ResponseStatus } from "@/datasources/domain/interface/responseStatus";
import { deleteProduct } from "@/services/deleteProductService";
import { progressAlert } from "@/utils/alert";
import { useCheckLogin } from "@/utils/checker";
import React, { useState } from "react";

interface DeleteProductProps {
  product: Product;
  onLoadData: () => void;
}

export const DeleteProduct: React.FC<DeleteProductProps> = ({
  product,
  onLoadData,
}) => {
  const [isSheetOpen, setIsSheetOpen] = useState(false);
  const { checkLogin } = useCheckLogin();

  const handleAction = async () => {
    setIsSheetOpen(!isSheetOpen);
  };

  const onSubmited = async () => {
    try {
      const response: ResponseStatus = await deleteProduct(product.id);
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
      <AcctionButton
        onClick={handleAction}
        icon="ri-delete-bin-line"
      ></AcctionButton>
      <BottomSheet isOpen={isSheetOpen} onClose={handleAction} height="50vh">
        <div className="title">
          <h2 className="text-xl font-bold mb-4">Delte Product</h2>
        </div>
        <div className="container mx-auto">
          <div className="body">
            <form
              action=""
              onSubmit={onSubmited}
              className="flex flex-col gap-3"
            >
              <p className="text-lg font-medium">
                Are you sure to delete this product?
              </p>
              <button className="w-full font-medium bg-blue-500 p-4 rounded-xl text-white">
                {"Yes, I'm sure"}
              </button>
            </form>
          </div>
        </div>
      </BottomSheet>
    </section>
  );
};
