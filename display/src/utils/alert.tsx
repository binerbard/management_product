import Swal from "sweetalert2";

export const progressAlert = (message: string, status: boolean) => {
  Swal.fire({
    position: "top-end",
    icon: status ? "success" : "error",
    title: message,
    showConfirmButton: false,
    color: "white",
    heightAuto: false,
    background: status ? "#34C759" : "#EC3A53",
    timer: 1500,
  });
};
