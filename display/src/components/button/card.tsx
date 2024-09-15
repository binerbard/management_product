import React from "react";
interface CardButtonProps {
  onClick: () => void;
  title: string;
  size: string;
}

export const CardButton: React.FC<CardButtonProps> = ({
  size,
  title,
  onClick,
}) => {
  return (
    <div>
      <div className="button-add">
        <button
          onClick={onClick}
          className={`w-${size} bg-blue-500 text-white p-3 rounded-xl`}
        >
          {title}
        </button>
      </div>
    </div>
  );
};
