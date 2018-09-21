/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProvaLuana.DAO;

import ProvaLuana.jdbc.ConnectionFactory;
import ProvaLuana.model.Custo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Luana Mora
 */
public class CustoDAO implements GenericDAO<Custo> {

    private Connection connection = null;

    @Override

    public void save(Custo entity) throws SQLException {
        try {
            this.connection = new ConnectionFactory().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("insert into custo(cd_custo, cd_destino, ds_custo,")
                    .append("tp_custo, vl_custo) values (?,?,?,?,?);");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getCodigo());
            pstm.setInt(2, entity.getCodigoDestino());
            pstm.setString(3, entity.getDescricao());
            pstm.setInt(4, entity.getTipo());
            pstm.setFloat(5, entity.getValor());

            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir Custo.", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            connection.close();
        }
    }

    @Override
    public void update(Custo entity) throws SQLException {
        try {
            this.connection = new ConnectionFactory().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("update custo set ds_custo = ?, ")
                    .append("cd_destino = ?, tp_custo= ?, ")
                    .append("vl_custo = ?");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setString(1, entity.getDescricao());
            pstm.setInt(2, entity.getCodigoDestino());
            pstm.setInt(3, entity.getTipo());
            pstm.setFloat(4, entity.getValor());

            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar Custo", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            connection.close();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "delete from custo where cd_custo = " + id;
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar Custo", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
    }

    @Override
    public Custo getById(int id) throws SQLException {
        Custo custo = null;
        try {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "select * from custo where cd_custo = " + id;
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            custo = new Custo();
            while (rs.next()) {
                custo.setCodigo(rs.getInt("cd_custo"));
                custo.setCodigoDestino(rs.getInt("cd_destino"));
                custo.setDescricao(rs.getString("ds_custo"));
                custo.setTipo(rs.getInt("tp_custo"));
                custo.setValor(rs.getFloat("vl_custo"));
            }
            pstm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar por  ID", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
        return custo;
    }

    @Override
    public List<Custo> getByName(String name) throws SQLException {
        Custo custo = null;
        List<Custo> custoList = null;
        try {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "select * from custo where upper(ds_custo) like upper('%" + name + "%')";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            custoList = new ArrayList<>();
            while (rs.next()) {
                custo = new Custo();
                custo.setCodigo(rs.getInt("cd_custo"));
                custo.setCodigoDestino(rs.getInt("cd_destino"));
                custo.setDescricao(rs.getString("ds_custo"));
                custo.setTipo(rs.getInt("tp_custo"));
                custo.setValor(rs.getFloat("vl_custo"));
                custoList.add(custo);
            }
            pstm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar por nome", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
        return custoList;
    }

    @Override
    public List<Custo> getAll() throws SQLException {
        List<Custo> custoList = null;
        Custo custo = null;
        try {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "select * from custo order by cd_custo";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            custoList = new ArrayList<>();
            while (rs.next()) {
                custo = new Custo();
                custo.setCodigo(rs.getInt("cd_custo"));
                custo.setCodigoDestino(rs.getInt("cd_destino"));
                custo.setDescricao(rs.getString("ds_custo"));
                custo.setTipo(rs.getInt("tp_custo"));
                custo.setValor(rs.getFloat("vl_custo"));
                custoList.add(custo);
            }
            pstm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar todos os custos", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
        return custoList;
    }

    @Override
    public int getLastId() throws SQLException {
        PreparedStatement pstm = null;
        try {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "select coalesce(max(cd_custo),0)+1 as maior from custo";
            pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt("MAIOR");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao mostrar  maior ID Custo", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            pstm.close();
            this.connection.close();
        }
        return 1;
    }
}
