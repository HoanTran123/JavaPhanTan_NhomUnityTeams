-- banve.dbo.hanh_khach definition

-- Drop table

-- DROP TABLE banve.dbo.hanh_khach;

CREATE TABLE banve.dbo.hanh_khach (
	maHK nvarchar(14) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ten nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	gioiTinh int DEFAULT 0 NOT NULL,
	soDienThoai nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	diaChi nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	soCmnd nvarchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ngayTao datetime DEFAULT getdate() NOT NULL,
	ngayCapNhat datetime DEFAULT getdate() NOT NULL,
	CONSTRAINT PK__hanh_kha__7A3E1489C908977D PRIMARY KEY (maHK),
	CONSTRAINT UQ__hanh_kha__BCED4313006C0F9E UNIQUE (soCmnd)
);


-- banve.dbo.khuyen_mai definition

-- Drop table

-- DROP TABLE banve.dbo.khuyen_mai;

CREATE TABLE banve.dbo.khuyen_mai (
	maKM nvarchar(14) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	soLuongPhatHanh int DEFAULT 0 NOT NULL,
	soLuongSuDung int DEFAULT 0 NOT NULL,
	trangThai int DEFAULT 0 NOT NULL,
	giaTriUuDai datetime DEFAULT getdate() NOT NULL,
	ngayCapNhat datetime DEFAULT getdate() NOT NULL,
	ngayTao datetime DEFAULT getdate() NOT NULL,
	CONSTRAINT PK__khuyen_m__7A3ECFFFB457A71C PRIMARY KEY (maKM)
);


-- banve.dbo.loai_ve definition

-- Drop table

-- DROP TABLE banve.dbo.loai_ve;

CREATE TABLE banve.dbo.loai_ve (
	maLoaiVe int IDENTITY(1,1) NOT NULL,
	trangThai int DEFAULT 0 NOT NULL,
	chietKhau int DEFAULT 0 NOT NULL,
	ngayTao datetime DEFAULT getdate() NOT NULL,
	ngayCapNhat datetime DEFAULT getdate() NOT NULL,
	CONSTRAINT PK__loai_ve__7AA7A7A75189D194 PRIMARY KEY (maLoaiVe)
);


-- banve.dbo.nhan_vien definition

-- Drop table

-- DROP TABLE banve.dbo.nhan_vien;

CREATE TABLE banve.dbo.nhan_vien (
	maNV nvarchar(14) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	ten nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NOT NULL,
	loai int DEFAULT 0 NOT NULL,
	gioiTinh int DEFAULT 0 NOT NULL,
	email nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	soDienThoai nvarchar(11) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	diaChi nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	trangThai int DEFAULT 0 NOT NULL,
	ngayTao datetime DEFAULT getdate() NOT NULL,
	ngayCapNhat datetime DEFAULT getdate() NOT NULL,
	CONSTRAINT PK__nhan_vie__7A3EC7D5090973A5 PRIMARY KEY (maNV),
	CONSTRAINT UQ__nhan_vie__AB6E616429888156 UNIQUE (email)
);


-- banve.dbo.tau definition

-- Drop table

-- DROP TABLE banve.dbo.tau;

CREATE TABLE banve.dbo.tau (
	maTau int IDENTITY(1,1) NOT NULL,
	ten nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	diemDen nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	diemDi nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	soToa int NOT NULL,
	loai int NOT NULL,
	trangThai int DEFAULT 0 NOT NULL,
	ngayTao datetime DEFAULT getdate() NOT NULL,
	ngayCapNhat datetime DEFAULT getdate() NOT NULL,
	maNV nvarchar(14) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	CONSTRAINT PK__tau__0FC5486FFC3C538D PRIMARY KEY (maTau),
	CONSTRAINT FK__tau__maNV__4D94879B FOREIGN KEY (maNV) REFERENCES banve.dbo.nhan_vien(maNV)
);


-- banve.dbo.thanh_toan definition

-- Drop table

-- DROP TABLE banve.dbo.thanh_toan;

CREATE TABLE banve.dbo.thanh_toan (
	maTT int IDENTITY(1,1) NOT NULL,
	trangThai int NOT NULL,
	loai int NOT NULL,
	ngayTao datetime DEFAULT getdate() NOT NULL,
	ngayCapNhat datetime DEFAULT getdate() NOT NULL,
	maHK nvarchar(14) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	CONSTRAINT PK__thanh_to__7A2262477594F7C4 PRIMARY KEY (maTT),
	CONSTRAINT FK__thanh_toan__maHK__47DBAE45 FOREIGN KEY (maHK) REFERENCES banve.dbo.hanh_khach(maHK)
);


-- banve.dbo.toa_tau definition

-- Drop table

-- DROP TABLE banve.dbo.toa_tau;

CREATE TABLE banve.dbo.toa_tau (
	maToa int IDENTITY(1,1) NOT NULL,
	ten nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	soGhe int NOT NULL,
	trangThai int DEFAULT 0 NOT NULL,
	soDayGhe int NOT NULL,
	soDay int NOT NULL,
	loai int NOT NULL,
	ngayTao datetime DEFAULT getdate() NOT NULL,
	ngayCapNhat datetime DEFAULT getdate() NOT NULL,
	maTau int NOT NULL,
	CONSTRAINT PK__toa_tau__0FC4DA7573161C0B PRIMARY KEY (maToa),
	CONSTRAINT FK__toa_tau__maTau__534D60F1 FOREIGN KEY (maTau) REFERENCES banve.dbo.tau(maTau)
);


-- banve.dbo.don_hang definition

-- Drop table

-- DROP TABLE banve.dbo.don_hang;

CREATE TABLE banve.dbo.don_hang (
	maDH nvarchar(14) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	tongTien decimal(10,2) DEFAULT 0 NOT NULL,
	gia decimal(10,2) DEFAULT 0 NOT NULL,
	trangThai int NOT NULL,
	ngayTao datetime DEFAULT getdate() NOT NULL,
	ngayCapNhat datetime DEFAULT getdate() NOT NULL,
	maHK nvarchar(14) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	maNV nvarchar(14) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	maTT int NOT NULL,
	maKM nvarchar(14) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	CONSTRAINT PK__don_hang__7A3EF40FF829AC26 PRIMARY KEY (maDH),
	CONSTRAINT FK__don_hang__maHK__7F2BE32F FOREIGN KEY (maHK) REFERENCES banve.dbo.hanh_khach(maHK),
	CONSTRAINT FK__don_hang__maKM__00200768 FOREIGN KEY (maKM) REFERENCES banve.dbo.khuyen_mai(maKM),
	CONSTRAINT FK__don_hang__maNV__7D439ABD FOREIGN KEY (maNV) REFERENCES banve.dbo.nhan_vien(maNV),
	CONSTRAINT FK__don_hang__maTT__7E37BEF6 FOREIGN KEY (maTT) REFERENCES banve.dbo.thanh_toan(maTT)
);


-- banve.dbo.ghe definition

-- Drop table

-- DROP TABLE banve.dbo.ghe;

CREATE TABLE banve.dbo.ghe (
	maGhe int IDENTITY(1,1) NOT NULL,
	loai int DEFAULT 0 NOT NULL,
	trangThai int DEFAULT 0 NOT NULL,
	ngayTao datetime DEFAULT getdate() NOT NULL,
	ngayCapNhat datetime DEFAULT getdate() NOT NULL,
	maToa int NOT NULL,
	CONSTRAINT PK__ghe__2D87404C435816E9 PRIMARY KEY (maGhe),
	CONSTRAINT FK__ghe__maToa__59FA5E80 FOREIGN KEY (maToa) REFERENCES banve.dbo.toa_tau(maToa)
);


-- banve.dbo.hanh_trinh definition

-- Drop table

-- DROP TABLE banve.dbo.hanh_trinh;

CREATE TABLE banve.dbo.hanh_trinh (
	maHT int IDENTITY(1,1) NOT NULL,
	gioChay datetime NOT NULL,
	ngayChay date NOT NULL,
	gioDen datetime NOT NULL,
	ngayDen date NOT NULL,
	loai int NOT NULL,
	trangThai int DEFAULT 0 NOT NULL,
	ngayTao datetime DEFAULT getdate() NOT NULL,
	ngayCapNhat datetime DEFAULT getdate() NOT NULL,
	maTau int NOT NULL,
	CONSTRAINT PK__hanh_tri__7A3E1496B482F653 PRIMARY KEY (maHT),
	CONSTRAINT FK__hanh_trin__maTau__5FB337D6 FOREIGN KEY (maTau) REFERENCES banve.dbo.tau(maTau)
);


-- banve.dbo.ve definition

-- Drop table

-- DROP TABLE banve.dbo.ve;

CREATE TABLE banve.dbo.ve (
	maVe int IDENTITY(1,1) NOT NULL,
	maLoaiVe int NOT NULL,
	trangThai int DEFAULT 0 NOT NULL,
	gia decimal(10,2) NOT NULL,
	ngayTao datetime DEFAULT getdate() NOT NULL,
	ngayCapNhat datetime DEFAULT getdate() NOT NULL,
	maHT int NOT NULL,
	maTau int NOT NULL,
	maToa int NOT NULL,
	maGhe int NOT NULL,
	CONSTRAINT PK__ve__7A227276831F27E8 PRIMARY KEY (maVe),
	CONSTRAINT FK__ve__maGhe__74AE54BC FOREIGN KEY (maGhe) REFERENCES banve.dbo.ghe(maGhe),
	CONSTRAINT FK__ve__maHT__72C60C4A FOREIGN KEY (maHT) REFERENCES banve.dbo.hanh_trinh(maHT),
	CONSTRAINT FK__ve__maLoaiVe__76969D2E FOREIGN KEY (maLoaiVe) REFERENCES banve.dbo.loai_ve(maLoaiVe),
	CONSTRAINT FK__ve__maTau__75A278F5 FOREIGN KEY (maTau) REFERENCES banve.dbo.tau(maTau),
	CONSTRAINT FK__ve__maToa__73BA3083 FOREIGN KEY (maToa) REFERENCES banve.dbo.toa_tau(maToa)
);


-- banve.dbo.chi_tiet_don_hang definition

-- Drop table

-- DROP TABLE banve.dbo.chi_tiet_don_hang;

CREATE TABLE banve.dbo.chi_tiet_don_hang (
	maCTHD int IDENTITY(1,1) NOT NULL,
	ngayTao datetime DEFAULT getdate() NOT NULL,
	ngayCapNhat datetime DEFAULT getdate() NOT NULL,
	maHK nvarchar(14) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	maDH nvarchar(14) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	maVe int NOT NULL,
	CONSTRAINT PK__chi_tiet__FD27BD7F4F5F515A PRIMARY KEY (maCTHD),
	CONSTRAINT FK__chi_tiet_d__maDH__05D8E0BE FOREIGN KEY (maDH) REFERENCES banve.dbo.don_hang(maDH),
	CONSTRAINT FK__chi_tiet_d__maHK__04E4BC85 FOREIGN KEY (maHK) REFERENCES banve.dbo.hanh_khach(maHK),
	CONSTRAINT FK__chi_tiet_d__maVe__06CD04F7 FOREIGN KEY (maVe) REFERENCES banve.dbo.ve(maVe)
);