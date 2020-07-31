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


/**
 *Contacts server for content and add that to page
 */
function getContent() {

    const languageCode = document.getElementById('languages').value;
    console.log(languageCode);

    const params = new URLSearchParams();
    params.append('languageCode', languageCode);

    var queryString = params.toString();

    fetch('/data/?' + queryString).then(response => response.json()).then(messages => {

        // Gets the element to add the comments
         const comments = document.getElementById('content-container');
         comments.innerHTML = "";
         messages.forEach((line) => {
         comments.appendChild(createListElement(line));
         });
         

        });
}

// function getTranslatedContent() {

//     const languageCode = document.getElementById('languages').value;
    
//     const params = new URLSearchParams();
//         params.append('languageCode', languageCode);

//     fetch('/data',{
          
//           body: params
//         }).then(response => response.json()).then(messages => {

//         // Gets the element to add the comments
//          const comments = document.getElementById('content-container');
//          messages.forEach((line) => {
//          comments.appendChild(createListElement(line));
//          });
         

//         });
// }

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}




