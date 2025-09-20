import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import CustomStyles from "@/styles/CustomStyles";
import CategoryStyles from "@/pages/order/CategoryStyles";

import { getCategory } from "../../config/api";
import Header from "@/components/Header";
import SignVideo from "@/components/SignVideo";
import video2 from "@/assets/videos/video2.mp4";

const CategoryButton = ({ category }) => {
  const [isPressed, setIsPressed] = useState(false);
  const navigate = useNavigate();

  const handlePressIn = () => setIsPressed(true);
  const handlePressOut = () => setIsPressed(false);
  const handleClick = () => navigate(category.to);

  return (
    <button
      onMouseDown={handlePressIn}
      onMouseUp={handlePressOut}
      onMouseLeave={handlePressOut}
      onTouchStart={handlePressIn}
      onTouchEnd={handlePressOut}
      onClick={handleClick}
      style={{
        ...CategoryStyles.categoryButton,
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
          fontSize: 52,
          lineHeight: "60px",
        }}
      >
        {category.icon}
      </span>
      {category.text}
    </button>
  );
};

const CategoryPage = () => {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    const fetchGetCategory = async () => {
      try {
        const category = await getCategory();
        setCategories(category.data.categories);
      } catch (error) {
        console.error(
          "카테고리 조회 오류:",
          error.response ? error.response.data : error.message
        );
      }
    };
    fetchGetCategory();
  }, []);

  const categoryIconMap = {
    커피: "☕️",
    차: "🌿",
    음료: "🧋",
    케이크: "🍰",
    빵: "🥯",
    샐러드: "🥗",
  };

  const mappedCategories = categories.map((item) => ({
    icon: categoryIconMap[item] || "",
    text: item,
    to: `/menu/${item}`,
  }));

  return (
    <div>
      <Header centerIcon="📋" />
      <div style={CategoryStyles.container}>
        <SignVideo srcList={[video2]} />
        <div style={CategoryStyles.containerCategory}>
          {mappedCategories.map((item, idx) => (
            <CategoryButton key={idx} category={item} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default CategoryPage;
