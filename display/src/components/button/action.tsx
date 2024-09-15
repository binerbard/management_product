import React from "react";

interface ActionButtonProps {
  onClick: () => void;
  icon: string;
}

export const AcctionButton: React.FC<ActionButtonProps> = ({
  icon,
  onClick,
}) => {
  return (
    <div>
      <button onClick={onClick}>
        <div className="button-action border-2 border-black px-3 py-2 rounded-full">
          <i className={`${icon}`}></i>
        </div>
      </button>
    </div>
  );
};
