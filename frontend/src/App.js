import "./App.css"
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import CustomerLogin from "./customer/CustomerLogin";
import CustomerSearchTeam from "./customer/CustomerSearchTeam";
import CustomerRegister from "./customer/CustomerRegister";
import AdminLogin from "./admin/AdminLogin";
import AdminHome from "./admin/AdminHome";
import VerifierLogin from "./verifier/VerifierLogin";
import VerifierHome from "./verifier/VerifierHome";
import AdminRegister from "./admin/AdminRegister";
import VerifierRegister from "./verifier/VerifierRegister";
import CustomerBets from "./customer/CustomerBets";
import CustomerWallet from "./customer/CustomerWallet";
import CustomerAllFixtures from "./customer/CustomerAllFixtures";

function App() {
    return (
        <Router>
            <Routes>
                <Route path={'/'} element={<CustomerRegister/>}/>
                <Route path={'/customer/login'} element={<CustomerLogin/>}/>
                <Route path={'/customer/searchTeam'} element={<CustomerSearchTeam/>}/>
                <Route path={'/customer/bets'} element={<CustomerBets/>}/>
                <Route path={'/customer/wallet'} element={<CustomerWallet/>}/>
                <Route path={'/customer/fixtures'} element={<CustomerAllFixtures/>}/>
                <Route path={'/admin/login'} element={<AdminLogin/>}/>
                <Route path={'/admin/home'} element={<AdminHome/>}/>
                <Route path={'/admin/register'} element={<AdminRegister/>}/>
                <Route path={'/verifier/login'} element={<VerifierLogin/>}/>
                <Route path={'/verifier/home'} element={<VerifierHome/>}/>
                <Route path={'/verifier/register'} element={<VerifierRegister/>}/>
            </Routes>
        </Router>
    );
}

export default App;