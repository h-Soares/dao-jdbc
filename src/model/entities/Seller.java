package model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Seller implements Serializable {
    private Integer id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private BigDecimal baseSalary; 
    private Department department;

    private static DateTimeFormatter datefmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static NumberFormat numberfmt = NumberFormat.getCurrencyInstance();

    public Seller() {
    }

    public Seller(Integer id, String name, String email, LocalDate birthDate, BigDecimal baseSalary, Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
        this.department = department;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID: " + id + ", ");
        sb.append("Name: " + name + ", ");
        sb.append("Email: " + email + ", ");
        sb.append("Birthday: " + birthDate.format(datefmt) + ", ");
        sb.append("Base Salary: " + numberfmt.format(baseSalary) + ", ");
        sb.append("Department: " + department);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Seller other = (Seller) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }   
}