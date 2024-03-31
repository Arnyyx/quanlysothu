/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Admin
 */
public class ModelNCC {

    private int IdNCC;
    private String TenNCC;
    private String LoaiThucAN;
    private String Vitri;

    public ModelNCC(String tenNCC, String loaiThucAn, String viTri) {

        this.TenNCC = tenNCC;
        this.LoaiThucAN = loaiThucAn;
        this.Vitri = viTri;
    }

    public ModelNCC() {

    }

    public int getIdNCC() {
        return IdNCC;
    }

    public void setIdNCC(int idNCC) {
        this.IdNCC = idNCC;
    }

    public String getTenNCC() {
        return TenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.TenNCC = tenNCC;
    }

    public String getLoaiThucAn() {
        return LoaiThucAN;
    }

    public void setLoaiThucAn(String loaiThucAn) {
        this.LoaiThucAN = loaiThucAn;
    }

    public String getViTri() {
        return Vitri;
    }

    public void setViTri(String viTri) {
        this.Vitri = viTri;
    }

    public void addSupplier(ModelNCC supplier, ArrayList<ModelNCC> supplierList) {
        supplierList.add(supplier);
    }

    public void updateSupplier(int id, ModelNCC updatedSupplier, ArrayList<ModelNCC> supplierList) {
        for (ModelNCC supplier : supplierList) {
            if (supplier.getIdNCC() == id) {
                supplierList.set(supplierList.indexOf(supplier), updatedSupplier);
                break;
            }
        }
    }

    public void deleteSupplier(int id, ArrayList<ModelNCC> supplierList) {
        for (Iterator<ModelNCC> iterator = supplierList.iterator(); iterator.hasNext();) {
            ModelNCC supplier = iterator.next();
            if (supplier.getIdNCC() == id) {
                iterator.remove();
            }
        }
    }

    public ModelNCC searchSupplierByName(String name, ArrayList<ModelNCC> supplierList) {
        for (ModelNCC supplier : supplierList) {
            if (supplier.getTenNCC().equalsIgnoreCase(name)) {
                return supplier;
            }
        }
        return null;
    }

    public void importFromExcel(String filePath, ArrayList<ModelNCC> supplierList) {
        // Implement importing data from Excel to supplier list
    }

    public void exportToExcel(String filePath, ArrayList<ModelNCC> supplierList) {
        // Implement exporting data from supplier list to Excel
    }

    public int calculateRemainingQuantity(ArrayList<ModelNCC> supplierList) {
        int totalQuantity = 0;
        for (ModelNCC supplier : supplierList) {
            // Calculate total quantity logic
        }
        return totalQuantity;
    }
}
