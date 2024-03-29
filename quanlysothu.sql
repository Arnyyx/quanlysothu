-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 29, 2024 lúc 02:34 PM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlysothu`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `habitatmanager`
--

CREATE TABLE `habitatmanager` (
  `ID` int(10) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `State` varchar(255) NOT NULL,
  `Area` float NOT NULL,
  `QuantityCurrent` int(10) NOT NULL,
  `Quantity` int(10) NOT NULL,
  `Img` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `habitatmanager`
--

INSERT INTO `habitatmanager` (`ID`, `Name`, `State`, `Area`, `QuantityCurrent`, `Quantity`, `Img`) VALUES
(15, 'Hổ', 'bad', 3, 3, 3, ''),
(23, '1', 'Normal', 1, 1, 1, 'anh-dep-cute-002-1.jpg'),
(24, 'Soi', 'bad', 24, 5, 10, 'boy.png'),
(25, 'Ho', 'bad', 24, 5, 10, 'boy.png'),
(26, 'Hooo', 'Super good', 24, 5, 10, 'home.png'),
(28, 'Doi', 'bad', 24, 5, 10, 'boy.png'),
(29, 'rong', 'Super good', 24, 5, 10, 'home.png'),
(30, 'miu miu', 'Super good', 24, 5, 6, 'anh-dep-cute-002-1.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ncc`
--

CREATE TABLE `ncc` (
  `IdNCC` int(11) NOT NULL,
  `TenNCC` varchar(255) NOT NULL,
  `LoaiThucAN` varchar(255) NOT NULL,
  `ViTri` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ncc`
--

INSERT INTO `ncc` (`IdNCC`, `TenNCC`, `LoaiThucAN`, `ViTri`) VALUES
(1, 'Hòa Bình', 'Đồ Khô', 'Hà Nội'),
(2, 'Đại Phát', 'Đồ sống', 'Hà Nội'),
(3, 'La La', 'Hoa quả', 'Hà Nội'),
(4, 'Đại La', 'Hoa quả', 'Long Biên'),
(5, 'Binh An', 'Hoa quả', 'Hà Đông'),
(6, 'An An', 'Hoa quả', 'Hà Nội'),
(7, 'Chí Thanh', 'Hoa Quả', 'HÀ Nội'),
(9, 'La Bình', 'Rau củ', 'Hà Nội');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE `nhanvien` (
  `ID` int(10) NOT NULL,
  `TenNV` varchar(50) NOT NULL,
  `MaNV` varchar(10) NOT NULL,
  `ChucVu` varchar(50) NOT NULL,
  `GioiTinh` varchar(10) NOT NULL,
  `NgaySinh` varchar(20) NOT NULL,
  `SDT` int(10) NOT NULL,
  `DiaChi` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `NgayVaoLam` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`ID`, `TenNV`, `MaNV`, `ChucVu`, `GioiTinh`, `NgaySinh`, `SDT`, `DiaChi`, `Email`, `NgayVaoLam`) VALUES
(1, 'Nguyen Huu Tu', 'QL01', 'Quản lý', 'Nam', '01/01/2003', 0, 'Cao Bang', 'HuuTu@gmail.com', '20/03/2024'),
(2, 'Duc Anh Vu', 'NV01', 'Nhân viên', 'Nam', '01/01/2003', 0, 'Ha Noi', 'DucAnhVu@gmail.com', '20/03/2024'),
(3, 'Xuan Duong Trinh', 'NV02', 'Nhân viên', 'Nam', '30/05/2003', 0, 'Ha Noi', 'XuanDuongTrinh@gmail.com', '20/03/2024'),
(4, 'Kim Chi Trinh', 'NV03', 'Nhân viên', 'Nữ', '01/01/2003', 0, 'Thanh Hoa', 'KimChiTrinh@gmail.com', '20/03/2024'),
(5, 'datngocc', 'NV04', 'Nhân viên', 'Nam', '09/10/2003', 0, 'Nam Dinh', 'datbeti913@gmail.com', '20/03/2024'),
(12, 'Trịnh Công Sơn', 'NV05', 'Nhân viên', 'Nam', '01/01/2000', 0, 'Hội An', 'congsongtrinh@gmail.com', '20/03/2024'),
(13, 'Hoàng Thùy Linh', 'NV06', 'Nhân viên', 'Nữ', '01/01/2000', 0, 'Cà Mau', 'thuylinhhoang@gmail.com', '20/03/2024'),
(14, 'Nguyễn Sơn Tùng', 'QL02', 'Quản Lý', 'Nam', '01/01/2000', 0, 'Thái Bình', 'sontung@gmail.com', '20/03/2024'),
(15, 'Đen không vâu', 'QL03', 'Quản Lý', 'Nam', '01/01/2000', 0, 'Hà Giang', 'denvau@gmail.com', '20/03/2024'),
(16, 'Nguyễn Thị Bích Ngọc', 'NV07', 'Nhân viên', 'Nữ', '17/04/2003', 0, 'Nam Định', 'ntbngocc@gmail.com', '20/03/2024'),
(17, 'Hiếu PC', 'QL04', 'Quản lý', 'Nam', '01/01/2000', 0, 'Hà Nội', 'pchieu@gmail.com', '20/03/2024'),
(18, 'Mono', 'NV08', 'Nhân viên', 'Nam', '01/01/2000', 1, 'Thái Bình', 'mono@gmail.com', '22/03/2024'),
(19, 'Cúc Kim Bùi', 'NV09', 'Nhân viên', 'Nữ', '01/01/20001', 2, 'Thái Bình', 'cuckimbui@gmail.com', '22/03/2024'),
(20, 'Tôi là ai', 'QL05', 'Quản lý', 'Nữ', '01/01/2000', 0, 'Kiên Giang', 'toilaai@gmail.com', '22/03/2024'),
(21, 'SuperMan', 'QL09', 'Quản lý', 'Nam', '01/01/2000', 0, 'Ngoài hành tinh', 'manofsuper@gmail.com', '22/03/2024'),
(22, 'SpiderMan', 'NV09', 'Nhân viên', 'Nam', '01/01/2000', 0, 'Americano', 'nguoinhensieudang@gmail.com', '22/03/2024'),
(23, 'IronMan', 'QL06', 'Quản lý', 'Nam', '01/01/1999', 0, 'Brazil', 'iamironman@gmail.com', '22/03/2024'),
(24, 'CaptainAmericano', 'QL07', 'Quản lý', 'Nữ', '01/01/2002', 0, 'Cần Thơ', 'captainlatoi@gmail.com', '22/03/2024'),
(25, 'J97', 'QL08', 'Quản lý', 'Nữ', '02/03/1987', 0, 'Nam Ấn Độ', 'jackdeeptry@gmail.com', '22/03/2024'),
(26, 'We bear bear', 'NV09', 'Nhân viên', 'Nam', '04/05/2000', 0, 'Sông Nile', 'gaucho@gmail.com', '22/03/2024');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tablechamsocdongvat`
--

CREATE TABLE `tablechamsocdongvat` (
  `IDChamSoc` int(11) NOT NULL,
  `TenDongVat` varchar(255) NOT NULL,
  `LoaiChamSoc` varchar(255) NOT NULL,
  `NgayChamSoc` datetime NOT NULL,
  `TenNhanVien` varchar(255) NOT NULL,
  `KetQua` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tablechamsocsuckhoe`
--

CREATE TABLE `tablechamsocsuckhoe` (
  `IDChamSoc` int(11) NOT NULL,
  `TenDongVat` varchar(255) NOT NULL,
  `LoaiChamSoc` varchar(255) NOT NULL,
  `NgayChamSoc` date NOT NULL,
  `TenNhanVien` varchar(255) NOT NULL,
  `KetQua` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tabledongvat`
--

CREATE TABLE `tabledongvat` (
  `IDDongVat` int(11) NOT NULL,
  `TenDongVat` varchar(255) NOT NULL,
  `TenChuong` varchar(255) DEFAULT NULL,
  `LoaiDongVat` varchar(255) NOT NULL,
  `TuoiDongVat` int(3) NOT NULL,
  `GioiTinhDongVat` varchar(255) NOT NULL,
  `TrangThaiDongVat` varchar(255) NOT NULL,
  `HinhAnhDongVat` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tabledongvat`
--

INSERT INTO `tabledongvat` (`IDDongVat`, `TenDongVat`, `TenChuong`, `LoaiDongVat`, `TuoiDongVat`, `GioiTinhDongVat`, `TrangThaiDongVat`, `HinhAnhDongVat`) VALUES
(153, 'Simba', 'Hổ', 'Sư tử', 5, 'Đực', 'Khoẻ mạnh', 'sutu.jpg'),
(154, 'Nala', 'Hổ', 'Sư tử', 4, 'Cái', 'Khoẻ mạnh', 'sutu.jpg'),
(155, 'Kiara', 'Hổ', 'Sư tử', 1, 'Cái', 'Khoẻ mạnh', 'sutu.jpg'),
(156, 'Timon', 'Hổ', 'Cầy vằn', 2, 'Đực', 'Khoẻ mạnh', 'vuoncao.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `ID` int(10) NOT NULL,
  `MaNV` varchar(50) NOT NULL,
  `TaiKhoan` varchar(50) NOT NULL,
  `MatKhau` varchar(50) NOT NULL,
  `Quyen` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`ID`, `MaNV`, `TaiKhoan`, `MatKhau`, `Quyen`) VALUES
(1, 'QL01', 'Tu', '1', 'QL'),
(2, 'NV01', 'DucAnh', '1', 'NV'),
(3, 'NV02', 'Duong', '1', 'NV'),
(4, 'NV03', 'Chi', '1', 'NV'),
(5, 'NV04', 'datngocc', '1', 'NV'),
(22, 'QL02', '1', '1', 'QL');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thucan`
--

CREATE TABLE `thucan` (
  `IDThucAN` int(11) NOT NULL,
  `TenThucAn` varchar(255) NOT NULL,
  `LoaiThucAn` varchar(255) NOT NULL,
  `IdNCC` int(11) NOT NULL,
  `IdDongVat` int(11) NOT NULL,
  `SoLuong` int(11) NOT NULL,
  `HanThucAn` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `thucan`
--

INSERT INTO `thucan` (`IDThucAN`, `TenThucAn`, `LoaiThucAn`, `IdNCC`, `IdDongVat`, `SoLuong`, `HanThucAn`) VALUES
(8, 'Cám', 'Đồ khô', 1, 1, 199, '2027'),
(11, 'Chuối', 'Hoa quả', 3, 2, 100, '2027'),
(12, 'Dâu Tây', 'Hoa Quả', 4, 5, 3344, '2025'),
(13, 'Cam', 'Hoa Quả', 5, 2, 333, '2025'),
(14, 'Nhooo', 'Hoa quả', 6, 4, 444, '2026'),
(15, 'Quýt', 'Hoa quả', 7, 6, 555, '2026'),
(16, 'dưa chuột', 'hoa quả', 7, 5, 5, '2034'),
(17, 'Cà rốt', 'Rau củ', 9, 6, 55, '2026'),
(18, 'Cần tây', 'Rau củ', 9, 5, 3344, '2025'),
(19, 'Thịt Bò', 'Đô sống', 2, 9, 222, '2222'),
(20, 'Khoai Lan', 'Rau củ', 9, 4, 3333, '2222'),
(21, 'Ngô', 'Rau củ', 9, 6, 202, '2026'),
(22, 'Hạt bí', 'Đồ Khô', 1, 5, 232, '2025'),
(23, 'Hướng Dương', 'Đồ Khô', 1, 4, 444, '2025'),
(24, 'Củ Từ', 'Rau củ', 9, 11, 111, '2026'),
(25, 'Rau muống', 'Rau củ', 9, 14, 222, '2022'),
(26, 'Con Cò', 'Đồ Khô', 1, 13, 33, '2028'),
(27, 'Thịt vịt', 'Đồ sống', 2, 16, 22, '2023'),
(28, 'Hạt Điều', 'Đồ khô', 1, 34, 333, '2024');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vethamquan`
--

CREATE TABLE `vethamquan` (
  `IdVe` int(11) NOT NULL,
  `IDNhanVien` int(11) NOT NULL,
  `TenVe` varchar(255) NOT NULL,
  `LoaiVe` varchar(255) NOT NULL,
  `GiaVe` int(11) NOT NULL,
  `SoLuong` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `vethamquan`
--

INSERT INTO `vethamquan` (`IdVe`, `IDNhanVien`, `TenVe`, `LoaiVe`, `GiaVe`, `SoLuong`) VALUES
(15, 1, '134567', 'Người lớn', 1, 1),
(16, 6, '3', 'Người lớn', 33, 3),
(17, 2, '2', 'Người lớn', 2, 2),
(18, 1, '1', 'Người lớn', 1, 1),
(19, 1, '2', 'Người lớn', 3, 4),
(20, 1, '1', 'Người lớn', 1, 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `habitatmanager`
--
ALTER TABLE `habitatmanager`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Name` (`Name`);

--
-- Chỉ mục cho bảng `ncc`
--
ALTER TABLE `ncc`
  ADD PRIMARY KEY (`IdNCC`);

--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `MaNV` (`MaNV`);

--
-- Chỉ mục cho bảng `tablechamsocdongvat`
--
ALTER TABLE `tablechamsocdongvat`
  ADD PRIMARY KEY (`IDChamSoc`);

--
-- Chỉ mục cho bảng `tablechamsocsuckhoe`
--
ALTER TABLE `tablechamsocsuckhoe`
  ADD PRIMARY KEY (`IDChamSoc`),
  ADD KEY `TenDongVat` (`TenDongVat`);

--
-- Chỉ mục cho bảng `tabledongvat`
--
ALTER TABLE `tabledongvat`
  ADD PRIMARY KEY (`IDDongVat`),
  ADD KEY `TenChuong` (`TenChuong`),
  ADD KEY `TenDongVat` (`TenDongVat`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Quyen` (`Quyen`),
  ADD KEY `MaNV` (`MaNV`),
  ADD KEY `Quyen_2` (`Quyen`);

--
-- Chỉ mục cho bảng `thucan`
--
ALTER TABLE `thucan`
  ADD PRIMARY KEY (`IDThucAN`),
  ADD KEY `thucan_ibfk_1` (`IdNCC`);

--
-- Chỉ mục cho bảng `vethamquan`
--
ALTER TABLE `vethamquan`
  ADD PRIMARY KEY (`IdVe`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `habitatmanager`
--
ALTER TABLE `habitatmanager`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT cho bảng `ncc`
--
ALTER TABLE `ncc`
  MODIFY `IdNCC` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT cho bảng `tablechamsocdongvat`
--
ALTER TABLE `tablechamsocdongvat`
  MODIFY `IDChamSoc` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `tablechamsocsuckhoe`
--
ALTER TABLE `tablechamsocsuckhoe`
  MODIFY `IDChamSoc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `tabledongvat`
--
ALTER TABLE `tabledongvat`
  MODIFY `IDDongVat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=157;

--
-- AUTO_INCREMENT cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT cho bảng `thucan`
--
ALTER TABLE `thucan`
  MODIFY `IDThucAN` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT cho bảng `vethamquan`
--
ALTER TABLE `vethamquan`
  MODIFY `IdVe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `tablechamsocsuckhoe`
--
ALTER TABLE `tablechamsocsuckhoe`
  ADD CONSTRAINT `tablechamsocsuckhoe_ibfk_1` FOREIGN KEY (`TenDongVat`) REFERENCES `tabledongvat` (`TenDongVat`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `tabledongvat`
--
ALTER TABLE `tabledongvat`
  ADD CONSTRAINT `tabledongvat_ibfk_1` FOREIGN KEY (`TenChuong`) REFERENCES `habitatmanager` (`Name`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD CONSTRAINT `taikhoan_ibfk_1` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`);

--
-- Các ràng buộc cho bảng `thucan`
--
ALTER TABLE `thucan`
  ADD CONSTRAINT `thucan_ibfk_1` FOREIGN KEY (`IdNCC`) REFERENCES `ncc` (`IdNCC`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
