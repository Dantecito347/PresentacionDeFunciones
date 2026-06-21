import Navbar from "../components/Navbar";
import SummaryCards from "../components/SummaryCards";
import SalesChart from "../components/SalesChart";
import SalesTable from "../components/SalesTable";
import ShareButton from "../components/ShareButton";

function Dashboard() {

    return (

        <>

            <Navbar/>

            <div className="container">

                <SummaryCards/>

                <SalesChart/>

                <SalesTable/>

                <ShareButton/>

            </div>

        </>

    );

}

export default Dashboard;