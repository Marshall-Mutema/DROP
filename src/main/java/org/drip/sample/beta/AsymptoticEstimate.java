
package org.drip.sample.beta;

import org.drip.function.definition.R2ToR1;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.beta.AsymptoticEstimator;
import org.drip.specialfunction.beta.LogGammaEstimator;
import org.drip.specialfunction.loggamma.NemesAnalyticEstimator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>AsymptoticEstimate</i> illustrates the Estimation and the Comparison of Asymptotic Estimates of the
 * Beta Function. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Davis, P. J. (1959): Leonhard Euler's Integral: A Historical Profile of the Gamma Function
 * 				<i>American Mathematical Monthly</i> <b>66 (10)</b> 849-869
 * 		</li>
 * 		<li>
 * 			Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge
 * 				University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Beta Function https://en.wikipedia.org/wiki/Beta_function
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Gamma Function https://en.wikipedia.org/wiki/Gamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/beta/README.md">Estimation Techniques for Beta Function</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AsymptoticEstimate
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[] xArray =
		{
			 5.,
			10.,
			15.,
			20.,
			25.
		};
		double[] yArray =
		{
			 5.,
			10.,
			15.,
			20.,
			25.
		};
		int logGammaTermCount = 1000000;

		R2ToR1 stirlingLogBetaEstimator = AsymptoticEstimator.Stirling();

		LogGammaEstimator logBetaEstimator = LogGammaEstimator.Weierstrass (logGammaTermCount);

		R2ToR1 largeXLogBetaEstimator = AsymptoticEstimator.LargeX (new NemesAnalyticEstimator (null));

		System.out.println ("\t|-------------------------------------------------------------||");

		System.out.println ("\t|       ASYMPTOTIC ESTIMATION OF THE LOG BETA FUNCTION        ||");

		System.out.println ("\t|-------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                              ||");

		System.out.println ("\t|                - x                                          ||");

		System.out.println ("\t|                - y                                          ||");

		System.out.println ("\t|                - Log Gamma Based Log Beta Estimation        ||");

		System.out.println ("\t|                - Stirling Asymptote Log Beta Estimation     ||");

		System.out.println ("\t|                - Large X Asymptote Log Beta Estimation      ||");

		System.out.println ("\t|-------------------------------------------------------------||");

		for (double x : xArray)
		{
			for (double y : yArray)
			{
				System.out.println (
					"\t|[" + FormatUtil.FormatDouble (x, 2, 0, 1., false) + ", " +
					FormatUtil.FormatDouble (y, 2, 0, 1., false) + "] => " +
					FormatUtil.FormatDouble (logBetaEstimator.evaluate (x, y), 2, 10, 1.) + " | " +
					FormatUtil.FormatDouble (stirlingLogBetaEstimator.evaluate (x, y), 2, 10, 1.) + " | " +
					FormatUtil.FormatDouble (largeXLogBetaEstimator.evaluate (x, y), 2, 10, 1.) + " ||"
				);
			}
		}

		System.out.println ("\t|-------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
