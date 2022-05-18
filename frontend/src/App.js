import "./App.css"
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import CustomerLogin from "./customer/CustomerLogin";
import CustomerHome from "./customer/CustomerHome";
import CustomerRegister from "./customer/CustomerRegister";

function App() {
    return (
        <Router>
            <Routes>
                <Route path={'/'} element={<CustomerLogin/>}/>
                <Route path={'/customer/register'} element={<CustomerRegister/>}/>
                <Route path={'/customer/home'} element={<CustomerHome/>}/>
            </Routes>
        </Router>
    );
}

export default App;