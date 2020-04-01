
package org.drip.sample.piterbarg2010;

import org.drip.exposure.csadynamics.FundingBasisEvolver;
import org.drip.measure.dynamics.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * <i>CSAFundingAbsoluteForward</i> compares the Absolute Differences between the CSA and the non-CSA Forward
 * 	LIBOR under a Stochastic Funding Model. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Barden, P. (2009): Equity Forward Prices in the Presence of Funding Spreads <i>ICBI
 *  			Conference</i> <b>Rome</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2009): Modeling and successful Management of Credit Counter-party Risk
 *  			of Derivative Portfolios <i>ICBI Conference</i> <b>Rome</b>
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Johannes, M., and S. Sundaresan (2007): Pricing Collateralized Swaps <i>Journal of Finance</i>
 *  			<b>62</b> 383-410
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/piterbarg2010/README.md">Piterbarg (2010) CSA Measure Extraction</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CSAFundingAbsoluteForward {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblUnderlyingVolatility = 0.3;
		double dblFundingSpreadVolatility = 0.015;
		double dblFundingSpreadMeanReversionRate = 0.05;
		double dblCSALIBOR = 0.018;

		double[] adblCorrelation = new double[] {
			-0.20,
			 0.00,
			 0.20,
			 0.40
		};

		int[] aiTenor = new int[] {
			 1,
			 2,
			 3,
			 4,
			 5,
			 7,
			10,
			15,
			20,
			25,
			30
		};

		DiffusionEvaluatorLogarithmic delUnderlying = DiffusionEvaluatorLogarithmic.Standard (
			0.,
			dblUnderlyingVolatility
		);

		DiffusionEvaluatorMeanReversion demrFundingSpread = DiffusionEvaluatorMeanReversion.Standard (
			dblFundingSpreadMeanReversionRate,
			0.,
			dblFundingSpreadVolatility
		);

		System.out.println();

		System.out.println ("\t||--------------------------------------------||");

		System.out.println ("\t||     DRIP CSA vs Non CSA Forward Rates      ||");

		System.out.println ("\t||--------------------------------------------||");

		String strHeader = "\t|| CORR => ";

		for (double dblCorrelation : adblCorrelation)
			strHeader = strHeader + "  " + FormatUtil.FormatDouble (dblCorrelation, 2, 0, 100.) + "%  |";

		System.out.println (strHeader + "|");

		System.out.println ("\t||--------------------------------------------||");

		for (int iTenor : aiTenor) {
			String strDump = "\t|| " + FormatUtil.FormatDouble (iTenor, 2, 0, 1.) + "Y => ";

			for (double dblCorrelation : adblCorrelation) {
				FundingBasisEvolver sftf = new FundingBasisEvolver (
					delUnderlying,
					demrFundingSpread,
					dblCorrelation
				);

				strDump = strDump + " " + FormatUtil.FormatDouble (dblCSALIBOR * (sftf.CSANoCSARatio (iTenor + "Y") - 1.), 1, 2, 100.) + "% |";
			}

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||--------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
