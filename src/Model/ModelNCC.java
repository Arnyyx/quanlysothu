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
private int idNCC;
private String tenNCC;
    private String loaiThucAn;
    private String viTri;

    public ModelNCC(int idNCC, String tenNCC, String loaiThucAn, String viTri) {
        this.idNCC = idNCC;
        this.tenNCC = tenNCC;
        this.loaiThucAn = loaiThucAn;
        this.viTri = viTri;
    }

    public int getIdNCC() {
        return idNCC;
    }

    public void setIdNCC(int idNCC) {
        this.idNCC = idNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getLoaiThucAn() {
        return loaiThucAn;
    }

    public void setLoaiThucAn(String loaiThucAn) {
        this.loaiThucAn = loaiThucAn;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
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
        for (Iterator<ModelNCC> iterator = supplierList.iterator(); iterator.hasNext(); ) {
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