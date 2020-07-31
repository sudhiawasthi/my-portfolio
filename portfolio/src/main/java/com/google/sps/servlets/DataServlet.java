// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.gson.Gson;
import java.util.ArrayList;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity; 
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/data/*")
public class DataServlet extends HttpServlet {


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    String queryString = request.getQueryString();
    int queryStringLength = queryString.length();
    //The language Code is present at last of the queryString with length always equal to 2
    String languageCode = queryString.substring(queryStringLength-2);

    Query query = new Query("Comments");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    // List to show comments
    ArrayList<String> messages = new ArrayList<String>();

    for (Entity entity : results.asIterable()) {

      String comment = (String) entity.getProperty("comment");

      //Do the translation
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        Translation translation =
        translate.translate(comment, Translate.TranslateOption.targetLanguage(languageCode));
        
        String translatedComment = translation.getTranslatedText();
        messages.add(translatedComment);
      }

    //Convert list to JSON 
    String json = convertToJson(messages);

    response.setContentType("application/json");
    response.getWriter().println(json);
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    // Take input from the form

    String userName = request.getParameter("user-name");
    String userComment = request.getParameter("user-comment");


    String comment = userName + " says " + userComment;

    // Add the comment to the list of comments

    Entity commentEntity = new Entity("Comments");
    commentEntity.setProperty("comment", comment);


    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    
    response.sendRedirect("/index.html");

  }


  private String convertToJson(ArrayList<String> messages)
  {
    Gson gson = new Gson();
    String json = gson.toJson(messages);
    return json;
  }
}
