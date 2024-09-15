"use client";
import { LoginButton } from "@/components/button/login";
import {
  LoginFormValues,
  LoginResponse,
} from "@/datasources/domain/interface/login";
import { LoginSchema } from "@/datasources/domain/schema/login";
import { login } from "@/services/authService";
import { yupResolver } from "@hookform/resolvers/yup";
import { useRouter } from "next/navigation";
import React from "react";
import { SubmitHandler, useForm } from "react-hook-form";

const loginSchema = LoginSchema;
export const LoginSection: React.FC = () => {
  const router = useRouter();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormValues>({
    resolver: yupResolver(loginSchema),
  });

  const onSubmit: SubmitHandler<LoginFormValues> = async (data) => {
    // Simulasi login
    try {
      const response: LoginResponse = await login(data.email, data.password);

      localStorage.clear();
      localStorage.setItem("token", response.data.token);
      localStorage.setItem("role", response.data.role);
      router.push("/product");
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <section className="login" id="login">
      <div className="container mx-auto">
        <div className="flex justify-center items-center h-[35rem]">
          <div className="container border-2 border-blue-500 p-3 m-3 rounded-xl md:w-[25rem]">
            <div className="title font-regular text-lg my-4">
              <h1>Welcome to Product Management</h1>
            </div>
            <div className="body my-4">
              <form
                action=""
                className="flex flex-col gap-3"
                onSubmit={handleSubmit(onSubmit)}
              >
                <div className="email">
                  <input
                    type="text"
                    placeholder="Enter Username"
                    {...register("email")}
                    className="w-full p-4 rounded-xl border-2 border-blue-200"
                  />
                  {errors.email && (
                    <p className="text-red-500 my-1 mx-2">
                      {errors.email.message}
                    </p>
                  )}
                </div>
                <div className="password">
                  <input
                    type="password"
                    placeholder="Enter Password"
                    {...register("password")}
                    className="w-full p-4 rounded-xl border-2 border-blue-200"
                  />
                  {errors.password && (
                    <p className="text-red-500 my-1 mx-2">
                      {errors.password.message}
                    </p>
                  )}
                </div>
                <LoginButton />
              </form>
            </div>
            <div className="credits text-center text-blue-950">
              <small>Copyright &copy; 2022. All rights reserved.</small>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
