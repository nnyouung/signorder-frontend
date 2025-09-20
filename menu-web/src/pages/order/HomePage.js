import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import CustomStyles from "@/styles/CustomStyles";
import HomeStyles from "@/pages/order/HomeStyles";

import SignVideo from "@/components/SignVideo";
import video1 from "@/assets/videos/video1.mp4";

const OrderButton = ({ icon, text, onClick }) => {
  const [isPressed, setIsPressed] = useState(false);

  const handlePressIn = () => setIsPressed(true);
  const handlePressOut = () => setIsPressed(false);

  return (
    <button
      onMouseDown={handlePressIn}
      onMouseUp={handlePressOut}
      onMouseLeave={handlePressOut}
      onTouchStart={handlePressIn}
      onTouchEnd={handlePressOut}
      onClick={onClick}
      style={{
        ...HomeStyles.orderButton,
        backgroundColor: isPressed
          ? CustomStyles.primaryBlue
          : CustomStyles.primaryGray,
        color: isPressed
          ? CustomStyles.primaryWhite
          : CustomStyles.primaryBlack,
      }}
    >
      <span
        style={{
          display: "block",
          fontSize: 80,
          lineHeight: "80px",
          marginBottom: 20,
        }}
      >
        {icon}
      </span>
      {text}
    </button>
  );
};

const HomePage = () => {
  const navigate = useNavigate();

  return (
    <>
      <p style={HomeStyles.headerIcon}>🏠</p>
      <div style={HomeStyles.container}>
        <SignVideo srcList={[video1]} />
        <div style={HomeStyles.containerButton}>
          <div style={{ flex: 1, marginRight: "3%" }}>
            <OrderButton
              icon="📋"
              text="주문하기"
              onClick={() => navigate("/category")}
            />
          </div>
          <div style={{ flex: 1, marginLeft: "3%" }}>
            <OrderButton
              icon="✅"
              text={<>주문 상태 확인</>}
              onClick={() => navigate("/check-order-number")}
            />
          </div>
        </div>
      </div>
    </>
  );
};

export default HomePage;
