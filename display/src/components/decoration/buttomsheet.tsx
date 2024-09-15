import React from "react";

interface BottomSheetProps {
  isOpen: boolean;
  onClose: () => void;
  height?: string;
  children: React.ReactNode;
}

const BottomSheet: React.FC<BottomSheetProps> = ({
  isOpen,
  onClose,
  children,
  height,
}) => {
  return (
    <>
      {isOpen && (
        <div
          className="fixed inset-0 bg-black bg-opacity-50 z-40"
          onClick={onClose} // Menutup Bottom Sheet ketika klik di luar
        ></div>
      )}
      <div
        className={`fixed bottom-0 left-0 right-0 z-50 bg-white rounded-t-lg transform transition-transform duration-300 ease-in-out ${
          isOpen ? "translate-y-0" : "translate-y-full"
        }`}
        style={{ height: height || "70vh" }} // Mengatur tinggi bottom sheet (bisa disesuaikan)
      >
        <div className="p-4">
          <div className="flex justify-end">
            <button
              onClick={onClose}
              className="text-gray-500 hover:text-gray-800"
            >
              <i className="ri-close-circle-fill"></i>
            </button>
          </div>
          {children} {/* Menampilkan konten yang diteruskan */}
        </div>
      </div>
    </>
  );
};

export default BottomSheet;
