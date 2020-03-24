package br.com.mobiplus.gitclient.data.issues

import br.com.mobiplus.gitclient.data.issues.model.IssuesResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IssuesApi {
    @GET("/repos/{ownerModel}/{gitRepoName}/issues")
    suspend fun getIssuesList(
        @Path("ownerModel") owner: String,
        @Path("gitRepoName") gitRepoName: String
    ): Response<List<IssuesResponseModel>>
}