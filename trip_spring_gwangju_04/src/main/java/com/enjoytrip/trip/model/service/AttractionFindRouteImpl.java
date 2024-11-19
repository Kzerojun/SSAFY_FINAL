package com.enjoytrip.trip.model.service;

import java.util.ArrayList;
import java.util.Arrays;

import com.enjoytrip.trip.model.AttractionDto;

public class AttractionFindRouteImpl implements AttractionFindRoute{
	private static final double R = 6371.0;  // 지구의 반지름 (단위: km)
	
	private static AttractionFindRoute attractionFindroute = new AttractionFindRouteImpl();
	
	private AttractionFindRouteImpl() {}
	
	public static AttractionFindRoute getAttractionFindroute() {
		if(attractionFindroute == null) attractionFindroute = new AttractionFindRouteImpl();
		return attractionFindroute;
	}
	@Override
	public AttractionDto[] findRoute(AttractionDto[] attractions) {
		
        int n = attractions.length;
        double[][] coordinates = new double[n][2];
        
        for(int i=0; i<n; i++) {
        	coordinates[i][0] = attractions[i].getLatitude();
        	coordinates[i][1] = attractions[i].getLongitude();
        }
        
        int[] path = new int[n];
        AttractionDto[] result = new AttractionDto[n];
        double shortestDistance = findShortestTSPRoute(coordinates, path);
        
        // 결과 출력
        System.out.printf("최단 경로의 총 거리: %.2f km\n", shortestDistance);
        System.out.print("방문 순서: ");
        for (int i = 0; i < n; i++) {
            System.out.print(path[i] + (i == n - 1 ? "\n" : " -> "));
            result[i] = attractions[path[i]];
        }
        
		return result;
	}
	
    // 위도, 경도가 주어진 좌표들에 대해 최단 경로 계산 함수
    public static double findShortestTSPRoute(double[][] coords, int[] path) {
        double[][] distMatrix = createDistanceMatrix(coords);
        return tspDP(distMatrix, path);
    }
	
    
    // Haversine 공식으로 위도, 경도를 이용해 두 점 사이의 거리 계산
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double deltaPhi = Math.toRadians(lat2 - lat1);
        double deltaLambda = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2)
                + Math.cos(phi1) * Math.cos(phi2) * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // km 단위로 반환
    }

    // 두 지점 간 거리를 미리 계산하여 거리 테이블을 생성
    public static double[][] createDistanceMatrix(double[][] coords) {
        int n = coords.length;
        double[][] distMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                distMatrix[i][j] = distMatrix[j][i] = haversine(
                        coords[i][0], coords[i][1], coords[j][0], coords[j][1]
                );
            }
        }
        return distMatrix;
    }

    // 동적 계획법을 사용한 TSP 해결 (Held-Karp 알고리즘) + 경로 복원
    public static double tspDP(double[][] distMatrix, int[] path) {
        int n = distMatrix.length;
        double[][] dp = new double[1 << n][n];  // dp[mask][i]
        int[][] parent = new int[1 << n][n];    // 경로 복원을 위한 부모 배열
        
        // dp 테이블을 무한대 값으로 초기화
        for (double[] row : dp) {
            Arrays.fill(row, Double.POSITIVE_INFINITY);
        }
        dp[1][0] = 0;  // 출발 도시는 0번으로 가정

        // 모든 부분 집합을 순회하며 경로를 채움
        for (int mask = 0; mask < (1 << n); mask++) {
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) == 0) {
                    continue;  // u 도시가 방문되지 않음
                }
                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) != 0) {
                        continue;  // v 도시가 이미 방문됨
                    }
                    int nextMask = mask | (1 << v);
                    double newCost = dp[mask][u] + distMatrix[u][v];
                    if (newCost < dp[nextMask][v]) {
                        dp[nextMask][v] = newCost;
                        parent[nextMask][v] = u;  // v 도시로 가기 전 u 도시에서 왔음을 기록
                    }
                }
            }
        }

        // 모든 도시를 방문한 후 종료하는 마지막 도시 찾기 (돌아오지 않음)
        double finalCost = Double.POSITIVE_INFINITY;
        int lastCity = -1;
        for (int i = 1; i < n; i++) {
            if (dp[(1 << n) - 1][i] < finalCost) {
                finalCost = dp[(1 << n) - 1][i];
                lastCity = i;
            }
        }

        // 최단 경로 복원
        int mask = (1 << n) - 1;
        int currentCity = lastCity;
        ArrayList<Integer> order = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            order.add(currentCity);
            int nextCity = parent[mask][currentCity];
            mask ^= (1 << currentCity);
            currentCity = nextCity;
        }
        order.add(0);  // 출발지 추가

        // 배열로 복사
        for (int i = 0; i < n; i++) {
            path[i] = order.get(n - 1 - i);
        }

        return finalCost;
    }

}
