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
import java.util.Date;

import java.util.List;
import ProvaLuana.model.Destino;
import javax.swing.JOptionPane;

/**
 *
 * @author Luana Mora
 */
public class CustoDAO implements GenericDAO<Custo> {

    private Connection connection = null;
    private DestinoDAO destinoDAO;

    @Override
    public void save(Custo entity) throws SQLException {
        try {
            this.connection = new ConnectionFactory().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("insert into custo(cd_custo, cd_destino, ds_custo, ")
                    .append("tp_custo, vl_custo) values (?,?,?,?,?)");
            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getCodigo());
            pstm.setInt(2, entity.getDestino().getCodigo());
            pstm.setString(3, entity.getDescricao());
            pstm.setInt(4, entity.getTipo());
            pstm.setDouble(5, entity.getValor());
            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir Custo");
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
            sql.append("update custo set cd_destino = ?, ")
                    .append("ds_custo = ?, tp_custo = ?, ")
                    .append("vl_custo = ? ")
                    .append("where cd_custo = ?");
            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getDestino().getCodigo());
            pstm.setString(2, entity.getDescricao());
            pstm.setInt(3, entity.getTipo());
            pstm.setDouble(4, entity.getValor());
            pstm.setInt(5, entity.getCodigo());
            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao Atualizar Custo");
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
            System.out.println("Erro ao deletar Custo");
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
    }

    @Override
    public Custo getById(int id) throws SQLException {
        Custo custo = null;
        DestinoDAO destinoDAO = new DestinoDAO();
        try{
            this.connection = new ConnectionFactory().getConnection();
            String sql = "SELECT * FROM CUSTO WHERE CD_CUSTO = "+id;
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            custo = new Custo();
            while (rs.next()) {
                custo.setCodigo(rs.getInt("CD_CUSTO"));
                custo.setDestino(destinoDAO.getById(rs.getInt("CD_DESTINO")));
                custo.setDescricao(rs.getString("DS_CUSTO"));
                custo.setTipo(rs.getInt("TP_CUSTO"));
                custo.setValor(rs.getFloat("VL_CUSTO"));
            }
            pstm.close();
        }catch (SQLException ex){
            System.out.println("Erro ao Fazer a busca de Custo por ID");
            ex.printStackTrace();
        }finally {
            this.connection.close();
        }
        return custo;
    }

    @Override
    public List<Custo> getByName(String name) throws SQLException {
        Custo custo = null;
        List<Custo> custoList = null;
        try{
            this.connection = new ConnectionFactory().getConnection();
            String sql = "SELECT * FROM CUSTO WHERE UPPER(DS_CUSTO) LIKE UPPER('%"+name+"%') ";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            custoList = new ArrayList<>();
            while (rs.next()) {
                custo = new Custo();
                custo.setCodigo(rs.getInt("CD_DESTINO"));
                custo.setDestino(destinoDAO.getById(rs.getInt("CD_DESTINO")));
                custo.setDescricao(rs.getString("DS_CUSTO"));
                custo.setTipo(rs.getInt("TP_CUSTO"));
                custo.setValor(rs.getFloat("VL_CUSTO"));
                custoList.add(custo);
            }
            pstm.close();
        }catch (SQLException ex){
            System.out.println("Erro ao consultar Custo por Descricao");
            ex.printStackTrace();
        }finally {
            this.connection.close();
        }
        return custoList;
    }

     @Override
    public List<Custo> getAll() throws SQLException {
        destinoDAO = new DestinoDAO();
        Custo custo = null;
        List<Custo> custoList = null;
        try{
            this.connection = new ConnectionFactory().getConnection();
            String sql = "SELECT * FROM CUSTO ORDER BY CD_CUSTO";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            custoList = new ArrayList<>();
            while (rs.next()) {
                custo = new Custo();
                custo.setCodigo(rs.getInt("CD_CUSTO"));
                custo.setDestino((destinoDAO.getById(rs.getInt("CD_DESTINO"))));
                custo.setDescricao(rs.getString("DS_CUSTO"));
                custo.setTipo(rs.getInt("TP_CUSTO"));
                custo.setValor(rs.getFloat("VL_CUSTO"));
                custoList.add(custo);
            }
            pstm.close();
        }catch (SQLException ex){
            System.out.println("Erro ao consultar todos os Custos");
            ex.printStackTrace();
        }finally {
            this.connection.close();
        }
        return custoList;
    } 

    @Override
    public int getLastId() throws SQLException {
        PreparedStatement pstm = null;
        try {
            this.connection = new ConnectionFactory().getConnection();
            String sql = "select coalesce(max(cd_custo), 0) + 1 as maior from custo";
            pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt("maior");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao maior ID Custo");
            ex.printStackTrace();
        } finally {
            pstm.close();
            this.connection.close();
        }
        return 1;
    }

}
