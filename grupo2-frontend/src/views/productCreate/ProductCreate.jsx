import { ArrowLeftIcon, MapPinIcon, PaperClipIcon, PhotoIcon, XMarkIcon } from '@heroicons/react/24/outline';
import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { Datalist, DatalistItem } from '../../components/datalist';
import { Input } from '../../components/input/Input';
import { BaseLayout } from '../../components/layout/BaseLayout';
import { HeaderNav } from '../../components/partials';
import { Textarea } from '../../components/textarea/Texarea';
import { useLoadingViewContext } from '../../context/LoadingViewContext';
import { useSearchContext } from '../../context/SearchContext';
import { useUserContext } from '../../context/UserContext';
import { FetchRoutes } from '../../guard/Routes';
import { usePreviewImage } from '../../hooks/useFilePreview';
import { FormStep } from './components/FormStep';
import { validation } from './utilities/validation';
import './productCreate.css'
import { CountSelect } from '../../components/partials/searchNavbar/components/CountSelect';
import { PropertyTypeSelect } from '../../components/partials/searchNavbar/components/PropertyTypeSelect';

export const ProductCreate = () => {

    const {
        status,
        startLoading,
        loadDone
      } = useLoadingViewContext()

      const { user } = useUserContext();
      const { categories, cities } = useSearchContext();
      const [features, setFeatures ] = useState(null);

      const navigate = useNavigate();

      const fetchData = async () => {
        try {
          startLoading();
          const { data : featuresList } = await axios.get(`${FetchRoutes.BASEURL}/feature`,
          { headers: { Authorization : user.authorization }})

          setFeatures(featuresList);

        } catch (error) {
          console.error(error.message);
          navigate('/bad-request')
        } finally{ loadDone() };
    }
    useEffect(() => {
        if (!features ) { fetchData() } 
    }, [])

    const initialState = {
        title: '',
        description : '',
        category: null,
        features: [],
        city: null,
        address : {
            address: '',
            number : '',
            floor : '',
            apartment: ''
        },
        guests: null,
        bathrooms: null,
        rooms: null,
        beds: null
    }

    const [ form, setForm ] = useState(initialState)

    const [ validated, setValidated ] = useState({
        general: null,
        information : null,
        features : null,
        policies : null,
        images : null
    })

    const validate = {
        general: validation.information,
        information : validation.information,
        features : validation.features,
        policies : validation.policies,
        images : validation.images
    }

    const incluidesFeature = id => form.features.includes(id);

    const addFeature = id => setForm({...form, features: [...form.features, id]});

    const removeFeature = id => setForm({...form, features: form.features.filter(item => item !== id)});


    const {images, changeHandler, removeHandler, addHandler} = usePreviewImage()

    // const initialState = {
    //     title: '',
    //     description : '',
    //     category: null,
    //     features: [],
    //     city: '',
    //     address : {
    //         address: '',
    //         number : '',
    //         floor : '',
    //         apartment: ''
    //     }
    // }

  return (
    <>
        <HeaderNav />
        <BaseLayout
          wrapperClassName="bg-logo-hero-search"
          padding='px-3 pt-20 lg:pt-24'
        >
            <div className='flex items-end justify-between w-full py-6'>
                <h1 className='text-2xl font-semibold text-white'>List new place</h1>
                <ArrowLeftIcon
                onClick={() => navigate(-1)}
                className="text-white w-10 h-10" />
            </div>
        </BaseLayout>
        <BaseLayout
          padding='px-3 pt-4 md:pt-6'
          className="relative mb-10 lg:grid lg:gap-8 product-create-container overflow-visible h-auto"
        >
            <div className='flex flex-col relative'>
                <FormStep
                status={validated.general}
                setStatus={e => setValidated({...validated, general: e})}
                initial
                validate={validate.general}
                title='General'>
                    <div className='mb-4'>
                        <h5 className='text-xl font-medium text-gray-600 px-2 mb-2'>Category</h5>
                        <div className='flex items-start gap-2 md:gap-4 cursor-pointer'>
                        {
                            categories?.map((category, i) => (
                                <PropertyTypeSelect
                                key={i}
                                name={category.title}
                                illustration={category.categoryIllustration.url}
                                onClick={() => setForm({...form, category: category.id})}
                                selected={form?.category === category.id} 
                                />
                            ))
                        }
                        </div>

                    </div>
                    <div className='flex flex-col lg:grid lg:grid-cols-2 gap-4 md:gap-2'>
                        <div>
                            <h5 className='text-lg font-medium text-gray-600 px-2'>Rooms</h5>
                            <CountSelect
                                value={form.rooms}
                                setValue={(value) => setForm({...form, rooms: value})}
                                />
                        </div>
                        <div>
                            <h5 className='text-lg font-medium text-gray-600 px-2'>Beds</h5>
                            <CountSelect 
                                value={form.beds}
                                setValue={(value) => setForm({...form, beds: value})}
                            />
                        </div>
                        <div>
                            <h5 className='text-lg font-medium text-gray-600 px-2'>Bathrooms</h5>
                            <CountSelect
                                value={form.bathrooms}
                                setValue={(value) => form({...form, bathrooms: value})}
                            />
                        </div>
                        <div>
                            <h5 className='text-lg font-medium text-gray-600 px-2'>Maxium guest</h5>
                            <CountSelect
                                value={form.guests}
                                setValue={(value) => form({...form, guests: value})}
                            />
                        </div>
                    </div>
                </FormStep>
                <FormStep
                status={validated.information}
                setStatus={e => setValidated({...validated, information: e})}
                reset={() => {}}
                validate={() => setValidated({...validated, information: validate.information()})}
                title='Basic information'>
                    
                    <div className='grid grid-cols-1 md:grid-cols-2 gap-6' >
                        <Input
                        label='Title'
                        id='product_title'
                        type='text'
                        placeholder='Exotic apartment in front of the sea...'
                        value={form.title}
                        onChange={(e) =>
                            setForm({ ...form, title: e.target.value })
                        }
                        />
                        <Datalist
                            label="City"
                            data={cities}
                            value={form.city ? `${form.city?.name},  ${form.city?.country.name}` : null}
                            id='locations_dsk_filter'
                            placeholder='Search around the world'
                            onChange={(e) => setForm({...form, city: e})}
                            resultRenderer={(city, i) => (
                            <DatalistItem
                                key={i}
                                name={`${city.name}, ${city.country.name}`}
                                id={city.id}
                                value={city}
                                icon={<MapPinIcon className='w-6 h-6 mr-2 text-violet-600' />}
                            />)}
                        />

                        <Input
                        label='Adress'
                        id='product_adress'
                        type='text'
                        placeholder='St Martin Street...'
                        value={form.address.address}
                        onChange={(e) =>
                            setForm({ ...form, adress: { ...adress, address: e.target.value }})
                        }/>
                        <div className='grid grid-cols-3 gap-2 md:gap-4'>
                            <Input
                            label='Number'
                            id='product_adress_number'
                            type='text'
                            placeholder='2301'
                            value={ form.address.number }
                            onChange={(e) =>
                                setForm({ ...form, adress: { ...adress, number: e.target.value }})
                            }/>
                            <Input
                            label='Floor'
                            id='product_adress_number'
                            type='text'
                            placeholder='2301'
                            value={ form.address.number }
                            onChange={(e) =>
                                setForm({ ...form, adress: { ...adress, number: e.target.value }})
                            }/>
                            <Input
                            label='Apartment'
                            id='product_adress_number'
                            type='text'
                            placeholder='2301'
                            value={ form.address.number }
                            onChange={(e) =>
                                setForm({ ...form, adress: { ...adress, number: e.target.value }})
                            }/>
                        </div>
                        <Textarea
                        label='Description'
                        id='product_description'
                        className='md:col-span-2'
                        placeholder='Brief description about the place, max. 260 characters'
                        value={form.description}
                        rows="4"
                        maxLength="160"
                        onChange={(e) =>
                            setForm({ ...form, description: e.target.value })
                        }
                        />
                    </div>
                    
                </FormStep>
                <FormStep
                status={validated.features}
                setStatus={e => setValidated({...validated, features: e})}
                validate={() => setValidated({...validated, features: validate.features()})}
                title='Features'>
                    <p className='text-gray-400 mb-4'>Please, select at least three features:</p>
                    <div className='grid grid-cols-3 md:grid-cols-5 lg:grid-cols-10 gap-6' >
                        {features?.map(feature => (
                            <div className={`flex flex-col flex-wrap items-center justify-start 
                            gap-2 p-2 rounded-lg bg-violet-50 text-gray-600
                            ${incluidesFeature( feature.id) ? '' : 'grayscale opacity-50 '}` 
                            }
                            onClick={ () => incluidesFeature( feature.id) ? removeFeature(feature.id) : addFeature(feature.id) }
                            key={feature.id}
                            >
                                <img
                                src={feature?.featureImage.url}
                                alt={feature?.featureImage.description}
                                className='w-10 h-10'
                                />
                                <p className='text-sm sm:text-base whitespace-nowrap 
                                max-w-[100%] overflow-hidden overflow-ellipsis'>{feature.name}</p>
                            </div>
                        ))}
                    </div>
                </FormStep>
                <FormStep
                status={validated.policies}
                setStatus={e => setValidated({...validated, policies: e})}
                validate={validate.policies}
                title='Policies'>

                </FormStep>
                <FormStep
                status={validated.images}
                setStatus={e => setValidated({...validated, images: e})}
                validate={validate.images}
                title='Add images'>                
                <p className='text-gray-400 mb-4'>Please, add at least three images:</p>
                <input
                name='product_photos'
                id='product_photos'
                multiple
                className='w-0 h-0 opacity-0'
                type='file'
                accept="image/*"
                onChange={(e) => images.length > 0 ? addHandler(e) : changeHandler(e) }
                />

                <div className='flex overflow-hidden grow-0'>
                    <div className='flex gap-4 overflow-x-auto snap-x snap-mandatory pb-4'>
                    { 
                    images.map((item, i) => (
                        <div key={i}  
                        className='w-64 h-64 rounded-xl overflow-hidden 
                        shrink-0 relative'>
                            <img src={item}
                            className="w-full h-full object-cover"
                            alt='image-preview' />
                            <button
                            onClick={() => removeHandler(i)}
                            className='w-10 h-10 absolute top-3 right-3 rounded-full
                            flex items-center justify-center bg-red-400'>
                                <XMarkIcon className='w-6 h-6 text-white' />
                            </button>
                        </div>
                    ))}
                    { 
                    images.length <= 10 &&
                        <label 
                        htmlFor='product_photos'
                        className='w-64 h-64 border-4 border-gray-400
                        flex flex-col items-center justify-center cursor-pointer
                        bg-gray-50 text-gray-400 rounded-xl shrink-0 '>
                            <p className='font-medium text-lg'>Add files</p>
                            <PaperClipIcon className='w-14 h-14' />
                        </label>      
                    }
                    </div>
                </div>
                </FormStep>
            </div>
            <article className='lg:sticky lg:top-32 lg:self-start'>
                <p className='font-semibold text-gray-900 text-xl my-4 '>
                    Preview
                </p>
                <div className='w-[350px] h-96 rounded-xl
                bg-white border-2 border-violet-700'>
                    <div className='flex items-center justify-center w-full h-60 p-3 overflow-hidden'>
                        {images.length > 0 ?
                        <img
                        src={images[0]}
                        alt='preview-image'
                        className='w-full h-full object-cover rounded-xl'
                        />
                        :
                        <div className='w-full h-full border-2
                        flex items-center justify-center 
                        text-gray-300 border-gray-300 rounded-xl'>
                            <PhotoIcon className='w-20 h-20 ' />
                        </div>
                        }
                    </div>
                    <div className='p-2 flex flex-col items-start text-left gap-1'>
                        <p className='font-medium text-xl text-gray-900 mb-1
                        max-w-[22ch] overflow-ellipsis whitespace-nowrap overflow-hidden'>{form.title ? form.title : 'Charming title'}</p>
                        <div className='flex items-center text-violet-700'>
                            <MapPinIcon className='w-6 h-6 mr-1'/>
                            <p className=''>{form.city ? `${form.city.name}, ${form.city.country.name}` : 'Chose a city'}</p>
                        </div>
                        <p className='ml-1 text-gray-500 max-h-40 
                        overflow-hidden overflow-ellipsis'>
                            {form.description ? form.description : 'brief description about the place, with a maxium of 260 characters.'}
                        </p>
                    </div>

                    <div className=''>
                        
                    </div>
                </div>
            </article>
        </BaseLayout>
    </>
  )
}
