
package org.drip.specialfunction.bessel;

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
 * <i>SphericalFirstEstimator</i> implements the Integral Estimator for the Spherical Bessel Function of the
 * First Kind. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Arfken, G. B., and H. J. Weber (2005): <i>Mathematical Methods for Physicists 6<sup>th</sup>
 * 				Edition</i> <b>Harcourt</b> San Diego
 * 		</li>
 * 		<li>
 * 			Temme N. M. (1996): <i>Special Functions: An Introduction to the Classical Functions of
 * 				Mathematical Physics 2<sup>nd</sup> Edition</i> <b>Wiley</b> New York
 * 		</li>
 * 		<li>
 * 			Watson, G. N. (1995): <i>A Treatise on the Theory of Bessel Functions</i> <b>Cambridge University
 * 				Press</b>
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Bessel Function https://en.wikipedia.org/wiki/Bessel_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Project</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/ode/README.md">Special Function Ordinary Differential Equations</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SphericalFirstEstimator extends
	org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator
{
	private org.drip.specialfunction.definition.BesselFirstKindEstimator _besselFirstKindEstimator = null;

	/**
	 * Retrieve the Order 0 Spherical Bessel First Kind Estimator
	 * 
	 * @return The Order 0 Spherical Bessel First Kind Estimator
	 */

	public static final org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator OrderZero()
	{
		return new org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator()
		{
			@Override public double smallJ (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (0. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("SphericalFirstEstimator::OrderZero::smallJ => Invalid Inputs");
				}

				return 0. == z ? 1. : java.lang.Math.sin (z) / z;
			}
		};
	}

	/**
	 * Retrieve the Order +1 Spherical Bessel First Kind Estimator
	 * 
	 * @return The Order +1 Spherical Bessel First Kind Estimator
	 */

	public static final org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator OrderPlusOne()
	{
		return new org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator()
		{
			@Override public double smallJ (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (1. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("SphericalFirstEstimator::OrderPlusOne::smallJ => Invalid Inputs");
				}

				return java.lang.Math.sin (z) / (z * z) - java.lang.Math.cos (z) / z;
			}
		};
	}

	/**
	 * Retrieve the Order -1 Spherical Bessel First Kind Estimator
	 * 
	 * @return The Order -1 Spherical Bessel First Kind Estimator
	 */

	public static final org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator OrderMinusOne()
	{
		return new org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator()
		{
			@Override public double smallJ (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (-1. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("SphericalFirstEstimator::OrderMinusOne::smallJ => Invalid Inputs");
				}

				return java.lang.Math.cos (z) / z;
			}
		};
	}

	/**
	 * Retrieve the Order +2 Spherical Bessel First Kind Estimator
	 * 
	 * @return The Order +2 Spherical Bessel First Kind Estimator
	 */

	public static final org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator OrderPlusTwo()
	{
		return new org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator()
		{
			@Override public double smallJ (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (2. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("SphericalFirstEstimator::OrderPlusTwo::smallJ => Invalid Inputs");
				}

				double oneOverZ = 1. / z;
				double oneOverZ2 = oneOverZ * oneOverZ;

				return (3. * oneOverZ2 - 1.) * java.lang.Math.sin (z) * oneOverZ -
					3. * java.lang.Math.cos (z) * oneOverZ2;
			}
		};
	}

	/**
	 * Retrieve the Order -2 Spherical Bessel First Kind Estimator
	 * 
	 * @return The Order -2 Spherical Bessel First Kind Estimator
	 */

	public static final org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator OrderMinusTwo()
	{
		return new org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator()
		{
			@Override public double smallJ (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (-2. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("SphericalFirstEstimator::OrderMinusTwo::smallJ => Invalid Inputs");
				}

				double oneOverZ = 1. / z;

				return -1. * java.lang.Math.cos (z) * oneOverZ * oneOverZ -
					java.lang.Math.sin (z) * oneOverZ;
			}
		};
	}

	/**
	 * Retrieve the Order +3 Spherical Bessel First Kind Estimator
	 * 
	 * @return The Order +3 Spherical Bessel First Kind Estimator
	 */

	public static final org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator
		OrderPlusThree()
	{
		return new org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator()
		{
			@Override public double smallJ (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (3. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("SphericalFirstEstimator::OrderPlusThree::smallJ => Invalid Inputs");
				}

				double oneOverZ = 1. / z;
				double oneOverZ2 = oneOverZ * oneOverZ;

				return (15. * oneOverZ2 * oneOverZ - 6. * oneOverZ) * java.lang.Math.sin (z) * oneOverZ -
					(15. * oneOverZ2 - 1.) * java.lang.Math.cos (z) * oneOverZ;
			}
		};
	}

	/**
	 * Retrieve the Order -3 Spherical Bessel First Kind Estimator
	 * 
	 * @return The Order -3 Spherical Bessel First Kind Estimator
	 */

	public static final org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator
		OrderMinusThree()
	{
		return new org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator()
		{
			@Override public double smallJ (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (-3. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("SphericalFirstEstimator::OrderMinusThree::smallJ => Invalid Inputs");
				}

				double oneOverZ = 1. / z;
				double oneOverZ2 = oneOverZ * oneOverZ;

				return (1. - 3. * oneOverZ2) * java.lang.Math.cos (z) * oneOverZ -
					3. * java.lang.Math.sin (z) * oneOverZ2;
			}
		};
	}

	/**
	 * Retrieve the Order -4 Spherical Bessel First Kind Estimator
	 * 
	 * @return The Order -4 Spherical Bessel First Kind Estimator
	 */

	public static final org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator
		OrderMinusFour()
	{
		return new org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator()
		{
			@Override public double smallJ (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (-4. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("SphericalFirstEstimator::OrderOrderMinusFour::smallJ => Invalid Inputs");
				}

				double oneOverZ = 1. / z;
				double oneOverZ2 = oneOverZ * oneOverZ;

				return (6. * oneOverZ - 15. * oneOverZ2 * oneOverZ) * java.lang.Math.cos (z) * oneOverZ -
					(15. * oneOverZ2 - 1.) * java.lang.Math.sin (z) * oneOverZ;
			}
		};
	}

	/**
	 * SphericalFirstEstimator Constructor
	 * 
	 * @param besselFirstKindEstimator Bessel Function First Kind Estimator
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public SphericalFirstEstimator (
		final org.drip.specialfunction.definition.BesselFirstKindEstimator besselFirstKindEstimator)
		throws java.lang.Exception
	{
		if (null == (_besselFirstKindEstimator = besselFirstKindEstimator))
		{
			throw new java.lang.Exception ("SphericalFirstEstimator Constructor => Invalid Inputs");
		}
	}

	@Override public double smallJ (
		final double alpha,
		final double z)
		throws java.lang.Exception
	{
		return java.lang.Math.sqrt (0.5 * java.lang.Math.PI / z) * _besselFirstKindEstimator.bigJ (
			alpha + 0.5,
			z
		);
	}

	/**
	 * Retrieve the Bessel Function First Kind Estimator
	 * 
	 * @return Bessel Function First Kind Estimator
	 */

	public org.drip.specialfunction.definition.BesselFirstKindEstimator besselFirstKindEstimator()
	{
		return _besselFirstKindEstimator;
	}
}
