package com.cpifppiramide.prueba1.dao.prendas;

import com.cpifppiramide.prueba1.context.DBConnection;
import com.cpifppiramide.prueba1.entidades.Ejemplar;
import com.cpifppiramide.prueba1.entidades.Prenda;
import com.cpifppiramide.prueba1.entidades.TipoPrenda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOPrendasMySQL implements DAOPrendas {

    @Override
    public List<Prenda> lista() {
        List<Prenda> prendas = new ArrayList<>();
        try {
            //aquí vamos a contar cuantos hay sin instanciar
            String query = "select *, (select count(*) from ejemplares e where e.prenda = p.marca) as numPrendas from prendas p";
            PreparedStatement statement = DBConnection.getInstance().prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Prenda prenda = new Prenda(rs.getString("marca"), TipoPrenda.valueOf(rs.getString("tipoPrenda")));
                Integer numPrendas = rs.getInt("numPrendas");
                for (int i = 0; i < numPrendas; i++) {
                    prenda.addEjemplar(new Ejemplar());
                }
                prendas.add(prenda);
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return prendas;
    }

    @Override
    public Prenda ver(String marca) {
        try {
            String query = "select * from prendas p where marca = ?";
            PreparedStatement statement = DBConnection.getInstance().prepareStatement(query);
            statement.setString(1,marca);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                return new Prenda(rs.getString("marca"), TipoPrenda.valueOf(rs.getString("tipoPrenda")));
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void inserta(Prenda prenda) {
        try {
            String query = "insert into prendas values (?,?)";
            PreparedStatement statement = DBConnection.getInstance().prepareStatement(query);
            statement.setString(1,prenda.getMarca());
            statement.setString(2, prenda.getTipoPrenda().toString());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
