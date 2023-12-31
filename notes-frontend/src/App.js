import './App.css';

import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Login } from './views/login/Login';
import { Create } from './views/create/Create';
import { Notes } from './views/notes/Notes';


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login/>}/>
        <Route path="/login" element={<Login/>}/>
        <Route path="/create" element={<Create/>}/>
        <Route path="/notes" element={<Notes/>}/>
        <Route exact path="/notes/:folderid/:noteid" element={<Notes/>}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
