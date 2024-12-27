    package bus;

    import Interface.ThongKe_Interface;
    import dao.ThongKe_dao;
    import java.util.ArrayList;

    public class ThongKe_bus implements ThongKe_Interface {

        private ThongKe_dao thongKeDao;

        public ThongKe_bus() {
            thongKeDao = new ThongKe_dao();
        }

        @Override
        public ArrayList<Object[]> thongKeVeTheoNhanVien(String nam) {
            return thongKeDao.thongKeVeTheoNhanVien(nam);
        }

        @Override
        public int thongKeTongVeDaBan(String nam) {
            return thongKeDao.thongKeTongVeDaBan(nam);
        }

        @Override
        public ArrayList<Object[]> thongKeNhanVien() {
            return thongKeDao.thongKeNhanVien();
        }

        @Override
        public ArrayList<Object[]> thongKeVeTheoTau(String nam) {
            return thongKeDao.thongKeVeTheoTau(nam);
        }

        @Override
        public ArrayList<Object[]> thongKeTop5NhanVien(String thang, String nam) {
            return thongKeDao.thongKeTop5NhanVien(thang, nam);
        }
    }