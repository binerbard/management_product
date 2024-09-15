import * as yup from "yup";
export const ProductSchema = yup.object().shape({
  product: yup.string().required("Product is required"),
  price: yup
    .number()
    .required("Price is required")
    .positive("Price must be positive"),
  description: yup.string().required("Description is required"),
});
