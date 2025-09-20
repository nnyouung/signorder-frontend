import React from "react";
import { useLocation } from "react-router-dom";

import Header from "@/components/Header";
import SignVideo from "@/components/SignVideo";
import video8 from "@/assets/videos/video8.mp4";

const OrderNumberPage = () => {
  const { state } = useLocation();

  return (
    <>
      <Header centerIcon={null} cartIcon={null} goTo="/" />

      <div
        style={{
          padding: "0 30px",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        {/* 카운터로가서휴대폰을보여주세요 */}
        <SignVideo srcList={[video8]} />

        <div
          style={{
            fontSize: 60,
            lineHeight: "68px",
            fontFamily: "Pretendard",
            fontWeight: 700,
            marginTop: 40,
          }}
        >
          {state?.orderNumber}
        </div>
      </div>
    </>
  );
};

export default OrderNumberPage;
