package DAL.DAO.DB;

import BE.Playlist;
import DAL.DB.DbConnectionHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDBDAO {
    protected List<Playlist> playlists;
    protected DbConnectionHandler database;

    public PlaylistDBDAO() {
        database = DbConnectionHandler.getInstance();
    }

    public List<Playlist> loadPlaylist() {
        var temp = new ArrayList<Playlist>();
        var con = database.getConnection();
        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM playlist;");
            while (rs.next()) {
                int id = rs.getInt("playlist_id");
                String name = rs.getString("playlist_name");
                temp.add(new Playlist(id, name));
            }

            return temp;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean createPlaylist(String name) {
        var con = database.getConnection();
        var sql = "INSERT INTO playlist (playlist_name) VALUES(?);";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Playlist getPlaylist(String name) {
        var con = database.getConnection();
        var sql = "SELECT FROM playlist WHERE playlist_name = ?;";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.executeUpdate();
            var resultSet = st.getResultSet();
            var id = resultSet.getInt("playlist_id");
            var name1 = resultSet.getString("playlist_name");
            var playlist = new Playlist(id, name1);
            return playlist;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean deletePlaylist(String name) {
        var con = database.getConnection();
        var sql = "DELETE FROM playlist WHERE playlist_name = ?;";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
