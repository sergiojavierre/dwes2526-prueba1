package com.cpifppiramide.prueba1.dao.ejemplares;

import com.cpifppiramide.prueba1.context.DBConnection;
import com.cpifppiramide.prueba1.entidades.Color;
import com.cpifppiramide.prueba1.entidades.Ejemplar;
import com.cpifppiramide.prueba1.entidades.Prenda;
import com.cpifppiramide.prueba1.entidades.Talla;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOEjemplaresMySQL implements DAOEjemplares{
    @Override
    public List<Ejemplar> get(Prenda prenda) {
        List<Ejemplar> ejemplares = new ArrayList<>();
        try {
            String query = "select * from ejemplares where prenda = ?";
            PreparedStatement statement = DBConnection.getInstance().prepareStatement(query);
            statement.setString(1,prenda.getMarca());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Ejemplar ejemplar = new Ejemplar(
                        prenda,
                        Color.valueOf(rs.getString("color")),
                        Talla.valueOf(rs.getString("talla")),
                        rs.getInt("stock")
                        );
                ejemplares.add(ejemplar);
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return ejemplares;
    }

    @Override
    public void inserta(Ejemplar ejemplar) {
        try {
            String query = "insert into ejemplares (prenda, color, talla, stock) values (?,?,?,?)";
            PreparedStatement statement = DBConnection.getInstance().prepareStatement(query);
            statement.setString(1,ejemplar.getPrenda().getMarca());
            statement.setString(2,ejemplar.getColor().toString());
            statement.setString(3,ejemplar.getTalla().toString());
            statement.setInt(4,ejemplar.getStock());
            statement.execute();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
