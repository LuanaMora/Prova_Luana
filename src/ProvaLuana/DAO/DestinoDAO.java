/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProvaLuana.DAO;

import java.sql.SQLException;
import ProvaLuana.model.Destino;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import javax.swing.JOptionPane;
import ProvaLuana.jdbc.ConnectionFactory;
import java.util.Date;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 *
 * @author Luana Mora
 */
public class DestinoDAO implements GenericDAO<Destino> {

    private Connection connection = null;

    @Override
    public void save(Destino entity) throws SQLException {
        try {
            this.connection = new ConnectionFactory().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("insert into destino(cd_destino, ds_destino, dt_inicio,")
                    .append("dt_termino, vl_total) values (?,?,?,?,?);");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getCodigo());
            pstm.setString(2, entity.getDescricao());
            pstm.setDate(3, entity.getDataInicio());
            pstm.setDate(4,  entity.getDataTermino());
            pstm.setFloat(5, entity.getValorTotal());

            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir Destino.", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            connection.close();
        }
    }

    @Override
    public void update(Destino entity) throws SQLException {
        try {
            this.connection = new ConnectionFactory().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("update destino set ds_destino = ?, ")
                    .append("cd_destino = ?, dt_inicio= ?, ")
                    .append("dt_termino = ?");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setString(1, entity.getDescricao());
            pstm.setInt(2, entity.getCodigo());
            //pstm.setDate(3, (Date) entity.getDataInicio());
           // pstm.setDate(4, (Date) entity.getDataTermino());

            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar Destino", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            connection.close();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "delete from destino where cd_destino = " + id;
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar Destino", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
    }

    @Override
    public Destino getById(int id) throws SQLException {
        Destino destino = null;
        try {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "select * from destino where cd_destino = " + id;
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            destino = new Destino();
            while (rs.next()) {
                destino.setCodigo(rs.getInt("cd_destino"));
                destino.setDescricao(rs.getString("ds_destino"));
                destino.setDataInicio(rs.getDate("dt_inicio"));
                destino.setDataTermino(rs.getDate("dt_termino"));
                destino.setValorTotal(rs.getFloat("vl_total"));
            }
            pstm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar por  ID", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
        return destino;
    }

    @Override
    public List<Destino> getByName(String name) throws SQLException {
        Destino destino = null;
        List<Destino> destinoList = null;
        try {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "select * from destino where upper(ds_destino) like upper('%" + name + "%')";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            destinoList = new ArrayList<>();
            while (rs.next()) {
                destino = new Destino();
                destino.setCodigo(rs.getInt("cd_destino"));
                destino.setDescricao(rs.getString("ds_destino"));
                destino.setDataInicio(rs.getDate("dt_inicio"));
                destino.setDataTermino(rs.getDate("dt_termino"));
                destino.setValorTotal(rs.getFloat("vl_total"));
                destinoList.add(destino);
            }
            pstm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar por nome", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
        return destinoList;
    }

    @Override
    public List<Destino> getAll() throws SQLException {
        List<Destino> destinoList = null;
        Destino destino = null;
        try {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "select * from destino order by cd_destino";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            destinoList = new ArrayList<>();
            while (rs.next()) {
                destino = new Destino();
                destino.setCodigo(rs.getInt("cd_destino"));
                destino.setDescricao(rs.getString("ds_destino"));
                destino.setDataInicio(rs.getDate("dt_inicio"));
                destino.setDataTermino(rs.getDate("dt_termino"));
                destino.setValorTotal(rs.getFloat("vl_total"));
                destinoList.add(destino);
            }
            pstm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar todos os destinos", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
        return destinoList;

    }

    @Override
    public int getLastId() throws SQLException {
        PreparedStatement pstm = null;
        try {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "select coalesce(max(cd_destino),0)+1 as maior from destino";
            pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt("MAIOR");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao mostrar  maior ID Destino", "ERRO", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            pstm.close();
            this.connection.close();
        }
        return 1;
    }
}
