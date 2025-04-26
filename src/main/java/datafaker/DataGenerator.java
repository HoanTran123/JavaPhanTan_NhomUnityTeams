//package datafaker;
//
//import entity.*;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityTransaction;
//import jakarta.persistence.Persistence;
//import net.datafaker.Faker;
//
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//
//public class DataGenerator {
//    private final Faker faker;
//    public static int maHDCount = 1;
//    public static int maNVCount = 1;
//    public static int maKHCount = 1;
//    public static int maThuocCount = 1;
//    public static int maVTCount = 1;
//
//    public DataGenerator() {
//        this.faker = new Faker();
//    }
//
//    private String generateMaHD() {
//        return String.format("HD%06d", maHDCount++);
//    }
//
//    private String generateMaNV() {
//        return String.format("NV%06d", maNVCount++);
//    }
//
//    private String generateMaKH() {
//        return String.format("KH%06d", maKHCount++);
//    }
//
//    private String generateMaThuoc() {
//        return String.format("SP%06d", maThuocCount++);
//    }
//
//    private String generateMaDM() {
//        return String.format("DM%03d", faker.number().numberBetween(1, 100));
//    }
//
//    private String generateMaDVT() {
//        return String.format("DVT%03d", faker.number().numberBetween(1, 100));
//    }
//
//    private String generateMaXX() {
//        return String.format("XX%03d", faker.number().numberBetween(1, 100));
//    }
//
//    private String generateMaNCC() {
//        return String.format("NCC%03d", faker.number().numberBetween(1, 100));
//    }
//
//    private String generateMaPDH() {
//        return String.format("PDH%06d", faker.number().numberBetween(1, 1000000));
//    }
//
//    private String generateMaPN() {
//        return String.format("PN%06d", faker.number().numberBetween(1, 1000000));
//    }
//
//    private String generateMaDT() {
//        return String.format("DT%06d", faker.number().numberBetween(1, 1000000));
//    }
//
//    private String generateMaVT() {
//        return String.format("VT%03d", maVTCount++);
//    }
//
//    private Date generateRandomDateWithinLast10Years() {
//        LocalDate now = LocalDate.now();
//        LocalDate tenYearsAgo = now.minusYears(10);
//        long startEpochDay = tenYearsAgo.toEpochDay();
//        long endEpochDay = now.toEpochDay();
//        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
//        return Date.from(LocalDate.ofEpochDay(randomDay).atStartOfDay(ZoneId.systemDefault()).toInstant());
//    }
//
//    public KhachHang generateKH() {
//        KhachHang khachHang = new KhachHang();
//        khachHang.setIdKH(generateMaKH());
//        khachHang.setHoTen(faker.name().fullName());
//        khachHang.setSdt(faker.phoneNumber().phoneNumber());
//        khachHang.setGioiTinh(faker.options().option("Nam", "Nữ"));
//        khachHang.setNgayThamGia(generateRandomDateWithinLast10Years());
//        khachHang.setHangMuc(faker.options().option("Thường", "VIP", "Premium"));
//        khachHang.setTongChiTieu(faker.number().randomDouble(2, 1000, 1000000));
//        return khachHang;
//    }
//
//    public NhanVien generateNV() {
//        NhanVien nhanVien = new NhanVien(idNV);
//        nhanVien.setIdNV(generateMaNV());
//        nhanVien.setHoTen(faker.name().fullName());
//        nhanVien.setSdt(faker.phoneNumber().phoneNumber());
//        nhanVien.setEmail(faker.internet().emailAddress());
//        nhanVien.setGioiTinh(faker.options().option("Nam", "Nữ"));
//        nhanVien.setNamSinh(faker.number().numberBetween(1930, 2020));
//        nhanVien.setNgayVaoLam(generateRandomDateWithinLast10Years());
//        return nhanVien;
//    }
//
//    public VaiTro generateVaiTro() {
//        VaiTro vaiTro = new VaiTro();
//        vaiTro.setIdVT(generateMaVT());
//        vaiTro.setTen(faker.options().option("Nhân viên bán hàng", "Quản lý"));
//        return vaiTro;
//    }
//
//    public TaiKhoan generateTK(NhanVien nhanVien) {
//        TaiKhoan taiKhoan = new TaiKhoan();
//        taiKhoan.setIdTK("TK" + nhanVien.getIdNV());
//        taiKhoan.setUsername(faker.name().username());
//        taiKhoan.setPassword(faker.internet().password(8, 16));
//        taiKhoan.setNhanVien(nhanVien);
//        taiKhoan.setVaiTro(new VaiTro());
//        return taiKhoan;
//    }
//
//    private Thuoc generateThuoc() {
//        Thuoc thuoc = new Thuoc();
//        thuoc.setIdThuoc(generateMaThuoc());
//        thuoc.setTenThuoc(faker.medication().drugName());
//        thuoc.setThanhPhan(String.join(", ", faker.lorem().words(3)));
//        thuoc.setSoLuongTon(faker.number().numberBetween(10, 1000));
//        thuoc.setGiaNhap(faker.number().randomDouble(2, 1000, 100000));
//        thuoc.setDonGia(faker.number().randomDouble(2, 10000, 1000000));
//        thuoc.setHanSuDung(LocalDate.now().plusDays(faker.number().numberBetween(30, 365)));
//        return thuoc;
//    }
//
//    public HoaDon generateHD(KhachHang khachHang, NhanVien nhanVien) {
//        HoaDon hoaDon = new HoaDon();
//        hoaDon.setIdHD(generateMaHD());
//        hoaDon.setThoiGian(new Date());
//        hoaDon.setKhachHang(khachHang);
//        hoaDon.setNhanVien(nhanVien);
//        hoaDon.setTongTien(0.0);
//        return hoaDon;
//    }
//
//    public ChiTietHoaDon generateChiTietHD(Thuoc thuoc, HoaDon hoaDon) {
//        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
//        chiTietHoaDon.setHoaDon(hoaDon);
//        chiTietHoaDon.setThuoc(thuoc);
//        chiTietHoaDon.setSoLuong(faker.number().numberBetween(1, 10));
//        chiTietHoaDon.setDonGia(thuoc.getDonGia());
//        return chiTietHoaDon;
//    }
//
//    public void generateData() {
//        EntityManager em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
//        EntityTransaction tr = em.getTransaction();
//        try {
//            tr.begin();
//            for (int i = 0; i < 10; i++) {
//                KhachHang khachHang = generateKH();
//                em.persist(khachHang);
//
//                NhanVien nhanVien = generateNV();
//                em.persist(nhanVien);
//
//                VaiTro vaiTro = generateVaiTro();
//                em.persist(vaiTro);
//
//                TaiKhoan taiKhoan = generateTK(nhanVien);
//                taiKhoan.setVaiTro(vaiTro);
//                em.persist(taiKhoan);
//
//                List<Thuoc> thuocList = new ArrayList<>();
//                for (int j = 0; j < 10; j++) {
//                    Thuoc thuoc = generateThuoc();
//                    em.persist(thuoc);
//                    thuocList.add(thuoc);
//                }
//
//                HoaDon hoaDon = generateHD(khachHang, nhanVien);
//                em.persist(hoaDon);
//
//                for (Thuoc thuoc : thuocList) {
//                    ChiTietHoaDon chiTietHoaDon = generateChiTietHD(thuoc, hoaDon);
//                    em.persist(chiTietHoaDon);
//                }
//            }
//            tr.commit();
//        } catch (Exception e) {
//            if (tr.isActive()) {
//                tr.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//    }
//
//    public static void main(String[] args) {
//        DataGenerator dg = new DataGenerator();
//        dg.generateData();
//    }
//}