import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import CheckOrderNumberStyles from "@/pages/checkOrder/CheckOrderNumberStyles";

import { getOrderNumber } from "../../config/api";
import Header from "@/components/Header";
import { ReactComponent as IconCheck } from "@/assets/icons/check.svg";
import Button from "@/components/Button";
import SignVideo from "@/components/SignVideo";
import BottomSheet from "@/components/BottomSheet";
import video9 from "@/assets/videos/video9.mp4";
import video12 from "@/assets/videos/video12.mp4";

const CheckOrderNumberPage = () => {
  const navigate = useNavigate();
  const [value, setValue] = useState("");
  const [isBottomSheetOpen, setIsBottomSheetOpen] = useState(false);

  const fetchGetOrderNumber = async () => {
    try {
      const category = await getOrderNumber(value);
      if (category) {
        navigate("/order-process", {
          state: {
            checkOrderNumber: value,
          },
        });
      }
    } catch (error) {
      console.error(
        "주문 과정 조회 오류:",
        error.response ? error.response.data : error.message
      );
      setIsBottomSheetOpen(true);
    }
  };

  return (
    <>
      <Header centerIcon="✅" cartIcon={null} />

      <div style={CheckOrderNumberStyles.container}>
        <input
          type="text"
          inputMode="numeric"
          pattern="[0-9]*"
          placeholder="주문번호 입력"
          value={value}
          onChange={(e) => setValue(e.target.value)}
          style={CheckOrderNumberStyles.inputOrderNumber}
        />

        <div style={{ margin: "0 0 70px 0" }}>
          {/* 주문번호를입력해주세요 */}
          <SignVideo srcList={[video9]} />
        </div>

        <Button
          icon={<IconCheck />}
          text="입력 완료"
          disabled={value.length === 0}
          onClick={fetchGetOrderNumber}
        />
      </div>

      {isBottomSheetOpen && (
        <BottomSheet onClose={() => setIsBottomSheetOpen(false)}>
          {/* 없는주문번호입니다 */}
          <SignVideo
            srcList={video12}
            onVideoEnd={() => setIsBottomSheetOpen(false)}
          />
        </BottomSheet>
      )}
    </>
  );
};

export default CheckOrderNumberPage;
