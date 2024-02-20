/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package socialmediaapplication;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Class representing a graph data structure to model social connections
public class Graph {
    private Map<Integer, List<Integer>> adjacencyList; // Map user ID to their connections
    
//    Constructor to initialize the graph
    public Graph() {
        adjacencyList = new HashMap<>(); // Initialize adjacency list
    }
    
//    Method to add a new user to the graph
    public void addUser(int userId) {
        // Add a new user to the graph
        adjacencyList.put(userId, new ArrayList<>());
        System.out.println("User Added Successfully. Id: "+userId );
    }

//    Method to add a connection between two users
     public void addConnection(int user1, int user2) {
        // Add an edge (connection) between user1 and user2
        adjacencyList.get(user1).add(user2);
        System.out.println(user1+"Follows"+user2);
        adjacencyList.get(user2).add(user1); // For an undirected graph
    }
     
//     Method to check if two users are connected (follow each other)
    public boolean isConnected(int user1, int user2) {
    List<Integer> connectionsOfUser1 = adjacencyList.get(user1);
    if (connectionsOfUser1 != null) {
        return connectionsOfUser1.contains(user2);
    } else {
        return false; // user1 doesn't exist in the graph
    }
    
}
//    Method to get the list of users followed by a given user
    public List<Integer> getConnections(int userId) {
        return adjacencyList.getOrDefault(userId, new ArrayList<>());
    }
//    Method to get the list of users who follow a given user
    public List<Integer> followingListOf(int userId) {
        return adjacencyList.getOrDefault(userId, new ArrayList<>());
    }
    // Get connections for a given user (users who follow the specified user)
    public List<Integer> getFollowers(int userId) {
        List<Integer> followers = new ArrayList<>();
        for (List<Integer> connections : adjacencyList.values()){
            if (connections.contains(userId)) {
                for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
                    if (entry.getValue().equals(connections)) {
                        followers.add(entry.getKey());
                        break;
                    }
                }
            }
        }
        return followers;
    }
//    Method to suggest users to follow for a given user based on mutual connections
    public List<Integer> suggestUsersToFollow(int userId) {
        List<Integer> mutualFollowers = new ArrayList<>();

        // Get the users followed by the given user
        List<Integer> following = followingListOf(userId);
        System.out.println(following);

        // Iterate through the users followed by the given user
        for (int followedUser : following) {
            // Get the followers of the followed user
            List<Integer> followersOfFollowedUser = getFollowers(followedUser);
            System.out.println("followers of the follwed user , i.e 1 are:"+followersOfFollowedUser);

            // Check if the followers of the followed user also follow the original user (userId)
            for (int follower : followersOfFollowedUser) {
                if (follower != userId && !following.contains(follower) && !mutualFollowers.contains(follower)) {
                    mutualFollowers.add(follower);
                }
            }
        }
        return mutualFollowers;
    }
    
//    Method to initialize the graph with users and connections from the database
    public static void initiateGraph( Graph socialGraph){
//       UserGraphStructure socialGraph = new UserGraphStructure();
        // Add users
       // Fetching users from the Databas
        Connection conn = dbConnection.dbconnect(); 


try {
    // Prepare SQL query
        String query = "SELECT UserId FROM Users";

        // Execute query
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        // Process the result set nd add users to the graph
        while (rs.next()) {
            socialGraph.addUser(rs.getInt("UserId"));
        }
        conn.close(); // Close database connection
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    // Establish another database connection for fetching connections
    Connection conn2= dbConnection.dbconnect();
    try {
        // Prepare SQL query
        String query = "SELECT follower_id, followee_id FROM Edge";

        // Execute query
        PreparedStatement pstmt = conn2.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        // Process the result set and add connections to the graph
        while (rs.next()) {
            int followerId = rs.getInt("follower_id");
            int followeeId = rs.getInt("followee_id");
            socialGraph.addConnection(followerId, followeeId);

        }
        conn2.close(); // Close database connection
    } catch (Exception ex) {
        ex.printStackTrace();
    } }

    public static void main(String[] args) {
        Graph socialGraph = new Graph();

        // Add users
        socialGraph.addUser(0);
        socialGraph.addUser(1);
        socialGraph.addUser(2);

        // Add connections
        socialGraph.addConnection(0, 1);
        socialGraph.addConnection(1, 2);

        // Get connections for a user
        int userToCheck = 1;
        List<Integer> connections = socialGraph.getConnections(userToCheck);
        System.out.println("Connections for User " + userToCheck + ": " + connections);
    }
    
}