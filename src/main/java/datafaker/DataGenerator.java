//package datafaker;
//
//import entity.*;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityTransaction;
//import jakarta.persistence.Persistence;
//import net.datafaker.Faker;
//
//import java.sql.Timestamp;
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
//    public static int maNCCCount = 1;
//    public static int maPNCount = 1;
//    private static long chiTietHoaDonIdCount = 1;
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
//    private String generateMaNCC() {
//        return String.format("NCC%06d", maNCCCount++);
//    }
//
//    private String generateMaPN() {
//        return String.format("PN%06d", maPNCount++);
//    }
//
//    private Long generateChiTietHoaDonId() {
//        return chiTietHoaDonIdCount++;
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
//    public void generateData() {
//        EntityManager em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
//        EntityTransaction tr = em.getTransaction();
//        try {
//            tr.begin();
//            for (int i = 0; i < 10; i++) {
//                // Generate and persist supporting entities
//                KhachHang khachHang = new KhachHang();
//                khachHang.setIdKH(generateMaKH());
//                khachHang.setHoTen(faker.name().fullName());
//                khachHang.setSdt(faker.phoneNumber().phoneNumber());
//                khachHang.setGioiTinh(faker.options().option("Nam", "Nữ").equals("Nam"));
//                khachHang.setEmail(faker.internet().emailAddress());
//                khachHang.setNgayThamGia(generateRandomDateWithinLast10Years());
//                em.persist(khachHang);
//
//                NhanVien nhanVien = new NhanVien();
//                nhanVien.setIdNV(generateMaNV());
//                nhanVien.setHoTen(faker.name().fullName());
//                nhanVien.setSdt(faker.phoneNumber().phoneNumber());
//                nhanVien.setEmail(faker.internet().emailAddress());
//                nhanVien.setGioiTinh(faker.options().option("Nam", "Nữ"));
//                em.persist(nhanVien);
//
//                NhaCungCap nhaCungCap = new NhaCungCap();
//                nhaCungCap.setIdNCC(generateMaNCC());
//                nhaCungCap.setTenNCC(faker.company().name());
//                nhaCungCap.setSdt(faker.phoneNumber().phoneNumber());
//                nhaCungCap.setDiaChi(faker.address().fullAddress());
//                em.persist(nhaCungCap);
//
//                // Generate and persist Thuoc
//                List<Thuoc> thuocList = new ArrayList<>();
//                for (int j = 0; j < 10; j++) {
//                    Thuoc thuoc = new Thuoc();
//                    thuoc.setIdThuoc(generateMaThuoc());
//                    thuoc.setTenThuoc(faker.lorem().word());
//                    thuoc.setSoLuongTon(faker.number().numberBetween(10, 1000));
//                    thuoc.setDonGia(faker.number().randomDouble(2, 10000, 1000000));
//                    thuocList.add(thuoc);
//                }
//
//                // Generate and persist HoaDon and ChiTietHoaDon
//                HoaDon hoaDon = new HoaDon();
//                hoaDon.setIdHD(generateMaHD());
//                hoaDon.setThoiGian(new Date());
//                hoaDon.setKhachHang(khachHang);
//                hoaDon.setNhanVien(nhanVien);
//                hoaDon.setTongTien(0.0);
//                em.persist(hoaDon);
//
//                for (Thuoc thuoc : thuocList.subList(0, Math.min(3, thuocList.size()))) {
//                    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
//                    chiTietHoaDon.setId(generateChiTietHoaDonId());
//                    chiTietHoaDon.setHoaDon(hoaDon);
//                    chiTietHoaDon.setThuoc(thuoc);
//                    chiTietHoaDon.setSoLuong(faker.number().numberBetween(1, 10));
//                    chiTietHoaDon.setDonGia(thuoc.getDonGia());
//                    em.persist(chiTietHoaDon);
//                    hoaDon.setTongTien(hoaDon.getTongTien() + (chiTietHoaDon.getSoLuong() * chiTietHoaDon.getDonGia()));
//                }
//                em.merge(hoaDon); // Update HoaDon with new tongTien
//
//                // Generate and persist PhieuNhap and ChiTietPhieuNhap
//                PhieuNhap phieuNhap = new PhieuNhap();
//                phieuNhap.setIdPN(generateMaPN());
//                phieuNhap.setThoiGian(new Timestamp(System.currentTimeMillis()));
//                phieuNhap.setNhaCungCap(nhaCungCap);
//                phieuNhap.setNhanVien(nhanVien);
//                phieuNhap.setTongTien(0.0);
//                em.persist(phieuNhap);
//
//                for (Thuoc thuoc : thuocList.subList(0, Math.min(3, thuocList.size()))) {
//                    ChiTietPhieuNhap chiTietPhieuNhap = new ChiTietPhieuNhap();
//                    chiTietPhieuNhap.setPhieuNhap(phieuNhap);
//                    chiTietPhieuNhap.setThuoc(thuoc);
//                    chiTietPhieuNhap.setSoLuong(faker.number().numberBetween(10, 100));
//                    chiTietPhieuNhap.setDonGia(thuoc.getDonGia());
//                    em.persist(chiTietPhieuNhap);
//                    phieuNhap.setTongTien(phieuNhap.getTongTien() + (chiTietPhieuNhap.getSoLuong() * chiTietPhieuNhap.getDonGia()));
//                }
//                em.merge(phieuNhap);
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
//    public Thuoc generateThuoc(DonViTinh donViTinh, DanhMuc danhMuc, XuatXu xuatXu) {
//        Thuoc thuoc = new Thuoc();
//        thuoc.setIdThuoc(generateMaThuoc());
//        thuoc.setTenThuoc(faker.lorem().word());
//        thuoc.setThanhPhan(String.join(", ", faker.lorem().words(3))); // Ensure thanhPhan is not null
//        thuoc.setDonvi(donViTinh);
//        thuoc.setDanhmuc(danhMuc);
//        thuoc.setXuatxu(xuatXu);
//        thuoc.setSoLuongTon(faker.number().numberBetween(10, 1000));
//        thuoc.setGiaNhap(faker.number().randomDouble(2, 1000, 100000));
//        thuoc.setDonGia(faker.number().randomDouble(2, 10000, 1000000));
//        thuoc.setHanSuDung(LocalDate.now().plusDays(faker.number().numberBetween(30, 365)));
//        return thuoc;
//    }
//    public static void main(String[] args) {
//        DataGenerator dg = new DataGenerator();
//        dg.generateData();
//    }
//}